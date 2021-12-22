package ru.lukas.langjunkie.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import ru.lukas.langjunkie.web.component.CardMapper;
import ru.lukas.langjunkie.web.dto.CardDto;
import ru.lukas.langjunkie.web.exception.CardNotFoundException;
import ru.lukas.langjunkie.web.model.Card;
import ru.lukas.langjunkie.web.model.ImageFileInfo;
import ru.lukas.langjunkie.web.model.User;
import ru.lukas.langjunkie.web.repository.CardRepository;
import ru.lukas.langjunkie.web.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${langjunkie.picture.path}")
    private String picturePath;

    @Override
    public void saveCard(CardDto cardDto, String username) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));
        Card card = cardMapper.toCardModel(cardDto);
        Optional<MultipartFile> picture = Optional.ofNullable(cardDto.getPicture());

        addPicture(picture, card);
        card.setUser(user);

        cardRepository.save(card);
    }

    @Override
    public void deleteCard(Long id) throws IOException {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("no card with such ID"));
        User user = userRepository.findByUsername(card.getUser().getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("wrong username"));
        Optional<ImageFileInfo> picture = Optional.ofNullable(card.getImage());

        deletePicture(picture, card);
        user.getCards().remove(card);

        userRepository.save(user);
    }

    @Override
    public void updateCard(CardDto cardDto) throws IOException {
        Card card = cardRepository.findById(cardDto.getId())
                .orElseThrow(() -> new CardNotFoundException("no card with such ID"));
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
    public void addCardImageToResponse(Long cardId, HttpServletResponse response) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("no card with such ID"));
        ImageFileInfo imageFileInfo = card.getImage();
        Path filePath = Paths.get(picturePath, imageFileInfo.getFilename());

        response.setContentType(imageFileInfo.getMimeType());
        response.setContentLength(imageFileInfo.getSize().intValue());
        response.setHeader(
                "Content-Disposition",
                "filename=\"" + imageFileInfo.getOriginalName() + "\"");

        try {
            Files.copy(filePath, response.getOutputStream());
        } catch (IOException e) {
            log.error("Unable to find {}. Error msg: {}", filePath, e.getMessage(), e);
        }
    }

    private void addPicture(Optional<MultipartFile> picture, Card card)
            throws IOException
    {
        if (picture.isPresent()) {
            if (!picture.get().isEmpty()) {
                ImageFileInfo imageFileInfo = createImageFileInfo(
                        Optional.ofNullable(picture.get().getOriginalFilename()).orElse(""),
                        picture.get()
                );

                imageFileInfo.setCard(card);
                Path filePath = Paths.get(picturePath).resolve(imageFileInfo.getFilename());
                Files.write(filePath, picture.get().getBytes());
                card.setImage(imageFileInfo);
            }
        }
    }

    private void deletePicture(Optional<ImageFileInfo> picture, Card card)
            throws IOException
    {
        if (picture.isPresent()) {
            Path oldFileName = Paths.get(picturePath).resolve(picture.get().getFilename());
            Files.delete(oldFileName);
            card.setImage(null);
            cardRepository.save(card);
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
