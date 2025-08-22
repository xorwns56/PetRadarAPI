package com.example.PetRadar.missing;

import com.example.PetRadar.user.User;
import com.example.PetRadar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
