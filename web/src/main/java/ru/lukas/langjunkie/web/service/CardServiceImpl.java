package ru.lukas.langjunkie.web.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ru.lukas.langjunkie.web.component.CardMapper;
import ru.lukas.langjunkie.web.dto.CardViewDto;
import ru.lukas.langjunkie.web.dto.CreateCardDto;
import ru.lukas.langjunkie.web.exception.CardNotFoundException;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.ImageFileInfo;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.CardRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Dmitry Lukashevich
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CardServiceImpl implements CardService {

    public static final String NO_CARD_WITH_SUCH_ID = "no card with such ID";

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final AmazonS3 s3client;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Override
    public void saveCard(CreateCardDto createCardDto, String username) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));
        Card card = cardMapper.toCardModel(createCardDto);
        Optional<MultipartFile> picture = Optional.ofNullable(createCardDto.getPicture());

        addPictureIfPresent(picture, card);
        card.setUser(user);

        cardRepository.save(card);
    }

    @Transactional
    @Override
    public void deleteCard(Long id) {
        Card card = cardRepository.findByIdEagerFetch(id)
                .orElseThrow(() -> new CardNotFoundException(NO_CARD_WITH_SUCH_ID));
        User user = card.getUser();
        Optional<ImageFileInfo> picture = Optional.ofNullable(card.getImage());

        deletePicture(picture);
        user.getCards().remove(card);

        userRepository.save(user);
    }

    @Override
    public void updateCard(CreateCardDto createCardDto) throws IOException {
        Card card = cardRepository.findById(createCardDto.getId())
                .orElseThrow(() -> new CardNotFoundException(NO_CARD_WITH_SUCH_ID));
        Optional<MultipartFile> picture = Optional.ofNullable(createCardDto.getPicture());
        Optional<ImageFileInfo> oldPicture = Optional.ofNullable(card.getImage());

        deletePicture(oldPicture);
        card.setImage(null);
        addPictureIfPresent(picture, card);

        card.setLanguage(createCardDto.getLanguage());
        card.setFrontSide(createCardDto.getFrontSide());
        card.setBackSide(createCardDto.getBackSide());
        card.getWord().setValue(createCardDto.getWord());

        cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    @Override
    public void addCardImageToResponse(Long cardId, HttpServletResponse response) {
        if (cardRepository.hasImage(cardId) && s3client.doesBucketExistV2(bucketName)) {
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new CardNotFoundException(NO_CARD_WITH_SUCH_ID));
            ImageFileInfo imageFileInfo = card.getImage();
            S3Object s3Object = s3client.getObject(bucketName, imageFileInfo.getFilename());

            response.setContentType(imageFileInfo.getMimeType());
            response.setContentLength(imageFileInfo.getSize().intValue());
            response.setHeader(
                    "Content-Disposition",
                    "filename=\"" + imageFileInfo.getOriginalName() + "\"");

            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();

                servletOutputStream.write(s3Object.getObjectContent().readAllBytes());
            } catch (IOException e) {
                log.error("Unable to find {}. Error msg: {}",
                        imageFileInfo.getFilename(), e.getMessage(), e);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CardViewDto> getAllCardViewByUserId(Long id, Pageable pageable) {
        return cardRepository.findAllCardViewDtoByUserId(id, pageable);
    }

    private void addPictureIfPresent(Optional<MultipartFile> picture, Card card)
            throws IOException, AmazonServiceException
    {
        if (picture.isPresent() && !picture.get().isEmpty()) {
            ImageFileInfo imageFileInfo = createImageFileInfo(
                    Optional.ofNullable(picture.get().getOriginalFilename()).orElse(""),
                    picture.get()
            );

            if (s3client.doesBucketExistV2(bucketName)) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(picture.get().getSize());
                s3client.putObject(new PutObjectRequest(
                        bucketName,
                        imageFileInfo.getFilename(),
                        picture.get().getInputStream(),
                        metadata));
            }

            card.setImage(imageFileInfo);
        }
    }

    private void deletePicture(Optional<ImageFileInfo> picture) {
        if (picture.isPresent() && s3client.doesBucketExistV2(bucketName)) {
            s3client.deleteObject(bucketName, picture.get().getFilename());
        }
    }

    private ImageFileInfo createImageFileInfo(String filename, MultipartFile picture) {
        String newFilename = UUID.randomUUID().toString();
        String extension = filename.substring(filename.indexOf('.'));

        return ImageFileInfo.builder()
                .filename(newFilename + extension)
                .mimeType(picture.getContentType())
                .originalName(picture.getOriginalFilename())
                .size(picture.getSize())
                .build();
    }
}
