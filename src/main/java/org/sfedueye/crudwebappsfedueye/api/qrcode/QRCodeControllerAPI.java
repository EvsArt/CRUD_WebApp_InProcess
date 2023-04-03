package org.sfedueye.crudwebappsfedueye.api.qrcode;

import org.sfedueye.crudwebappsfedueye.api.qrcode.QRPermanentGenerator;
import org.sfedueye.crudwebappsfedueye.api.qrcode.RequestFormByQRCode;
import org.sfedueye.crudwebappsfedueye.api.qrcode.QRResponseForm;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/qr",
                produces = "application/json")
public class QRCodeControllerAPI {

    final UserRepository repository;

    public QRCodeControllerAPI(UserRepository repository) {
        this.repository = repository;
    }

    // DELETE ME
    @GetMapping // DELETE ME
    public String f(){ // DELETE ME
        return QRPermanentGenerator.getKey();   // DELETE ME
    }   // DELETE ME
    // DELETE ME

    @PostMapping(consumes = "application/json")
    public QRResponseForm checkUserAfterScanningQR(@RequestBody RequestFormByQRCode form) {
        User user;

        if(!form.getRandomKey().equals(QRPermanentGenerator.getKey()))
            return new QRResponseForm(false, "Попробуйте ещё раз");

        if (!repository.existsById(form.getUserId()))
            return new QRResponseForm(false, "Вас нет в нашей базе данных!");

        user = repository.findUserById(form.getUserId());
        if (form.getDataHashCode() != user.hashCode() || !user.isEnabled()) {
            System.out.println(form.getDataHashCode());
            System.out.println(user.hashCode());
            return new QRResponseForm(false, "Данные пользователя не совпадают с данными на сервере или пользователь заблокирован");
        }



        return new QRResponseForm(true, "Доступ разрешён");

        //TODO(Организовать какое-то действие в случае успеха)
    }


}


