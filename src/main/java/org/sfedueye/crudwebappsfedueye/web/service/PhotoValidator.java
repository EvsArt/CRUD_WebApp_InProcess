package org.sfedueye.crudwebappsfedueye.web.service;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoValidator implements Validator {

    @Override
    public void validate(Object uploadedFile, Errors errors) {

        MultipartFile file = (MultipartFile) uploadedFile;

        if (file.getSize() == 0) {
            errors.rejectValue("photoReq", "uploadForm.sizeIsNull",
                    "Пожалуйста, прикрепите фото!");
        } else if (file.getSize() > 10485760) {
            errors.rejectValue("photoReq", "uploadForm.sizeIsBig",
                    "Размер фото не должен превышать 10Мб");
        } else if (!file.getContentType().split("/")[0].equals("image")){
            errors.rejectValue("photoReq", "uploadForm.isNotAnImage",
                    "Пожалуйста, не прикрепляйте файлы, не являющиеся изображениями!");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

}
