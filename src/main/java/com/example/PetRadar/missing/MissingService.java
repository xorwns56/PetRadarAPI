package com.example.PetRadar.missing;

import com.example.PetRadar.user.User;
import com.example.PetRadar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissingService {
    private final MissingRepository missingRepository;
    private final UserRepository userRepository;

    public void createMissing(long userId, MissingDTO missingDTO) {
        Missing missing = new Missing();
        User user = userRepository.getReferenceById(userId);
        missing.setUser(user);
        missing.setPetName(missingDTO.getPetName());
        missing.setPetType(missingDTO.getPetType());
        missing.setPetGender(missingDTO.getPetGender());
        missing.setPetBreed(missingDTO.getPetBreed());
        missing.setPetAge(missingDTO.getPetAge());
        missing.setPetMissingDate(missingDTO.getPetMissingDate());
        missing.setPetMissingPlace(missingDTO.getPetMissingPlace());
        missing.setLatitude(missingDTO.getPetMissingPoint().getLat());
        missing.setLongitude(missingDTO.getPetMissingPoint().getLng());
        missing.setPetImage(missingDTO.getPetImage());
        missing.setTitle(missingDTO.getTitle());
        missing.setContent(missingDTO.getContent());
        missingRepository.save(missing);
    }

    public List<MissingDTO> getMissingList() {
        return missingRepository.findAll()
                .stream()
                .map(missing -> new MissingDTO(
                        missing.getId(),
                        missing.getUser().getId(),
                        missing.getPetName(),
                        missing.getPetType(),
                        missing.getPetGender(),
                        missing.getPetBreed(),
                        missing.getPetAge(),
                        missing.getPetMissingDate(),
                        missing.getPetMissingPlace(),
                        new MissingDTO.PetMissingPoint(missing.getLatitude(), missing.getLongitude()),
                        missing.getPetImage(),
                        missing.getTitle(),
                        missing.getContent()
                ))
                .collect(Collectors.toList());

    }

    public MissingDetailDTO getMissingDetail(Long id) {
        Missing missing = missingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 없습니다."));
        return new MissingDetailDTO(
                missing.getId(),
                missing.getUser().getId(),
                missing.getPetName(),
                missing.getPetType(),
                missing.getPetGender(),
                missing.getPetBreed(),
                missing.getPetAge(),
                missing.getPetMissingDate(),
                missing.getPetMissingPlace(),
                new MissingDTO.PetMissingPoint(missing.getLatitude(), missing.getLongitude()),
                missing.getPetImage(),
                missing.getTitle(),
                missing.getContent()
        );
    }
}
