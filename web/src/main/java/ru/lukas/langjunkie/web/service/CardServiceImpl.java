package ru.lukas.langjunkie.web.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.lukas.langjunkie.web.component.CardMapper;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.CardRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Dmitry Lukashevich
 */
@Service
@RequiredArgsConstructor
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
        Optional<String> picture = Optional.ofNullable(card.getPicturePath());

        deletePicture(picture, card);

        user.getCards().remove(card);

        userRepository.save(user);
    }

    @Override
    public void updateCard(CardDto cardDto) throws IOException {
        Card card = cardRepository.findById(cardDto.getId()).orElseThrow();
        Optional<MultipartFile> picture = Optional.ofNullable(cardDto.getPicture());
        Optional<String> oldPicture = Optional.ofNullable(card.getPicturePath());

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
                String fileName = picture.get().getOriginalFilename().replaceAll(" ", "_");
                
                if (s3client.doesBucketExistV2(bucketName)) {
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(picture.get().getSize());
                    s3client.putObject(new PutObjectRequest(bucketName, fileName, picture.get().getInputStream(), metadata));
                }

                card.setPicturePath(fileName);
            }
        }
    }

    private void deletePicture(Optional<String> picture, Card card) {
        if (picture.isPresent()) {
            if (s3client.doesBucketExistV2(bucketName)) {
                s3client.deleteObject(bucketName, picture.get());
            }

            card.setPicturePath(null);
        }
    }
}
