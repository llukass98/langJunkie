package ru.lukas.langjunkie.web.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.lukas.langjunkie.web.component.CardMapper;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.ImageFileInfo;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.CardRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Dmitry Lukashevich
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final AmazonS3 s3client;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

    @Override
    public void saveCard(CardDto cardDto, String username) throws IOException {
        User user = userRepository.findByUsername(username);
        Card card = cardMapper.toCardModel(cardDto);
        Optional<MultipartFile> picture = Optional.ofNullable(cardDto.getPicture());

        addPicture(picture, card);
        card.setUser(user);
        user.getCards().add(card);

        userRepository.save(user);
    }

    @Override
    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(card.getUser().getUsername());
        Optional<ImageFileInfo> picture = Optional.ofNullable(card.getImage());

        deletePicture(picture, card);
        user.getCards().remove(card);

        userRepository.save(user);
    }

    @Override
    public void updateCard(CardDto cardDto) throws IOException {
        Card card = cardRepository.findById(cardDto.getId()).orElseThrow();
        Optional<MultipartFile> picture = Optional.ofNullable(cardDto.getPicture());
        Optional<ImageFileInfo> oldPicture = Optional.ofNullable(card.getImage());

        deletePicture(oldPicture, card);
        addPicture(picture, card);

        card.setLanguage(cardDto.getLanguage());
        card.setFrontSide(cardDto.getFrontSide());
        card.setBackSide(cardDto.getBackSide());
        card.getWord().setWord(cardDto.getWord());

        cardRepository.save(card);
    }

    @Override
    public Long getNumberOfCardsByUser(UserDto userDto) {
        Long userId = userRepository.findByUsername(userDto.getUsername()).getId();

        return cardRepository.countByUserId(userId);
    }

    private void addPicture(Optional<MultipartFile> picture, Card card)
            throws IOException, AmazonServiceException
    {
        if (picture.isPresent()) {
            if (!picture.get().isEmpty()) {
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
    }

    private void deletePicture(Optional<ImageFileInfo> picture, Card card) {
        if (picture.isPresent()) {
            if (s3client.doesBucketExistV2(bucketName)) {
                s3client.deleteObject(bucketName, picture.get().getFilename());
            }

            card.setImage(null);
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
