package org.sfedueye.crudwebappsfedueye.api;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.hibernate.internal.log.SubSystemLogging;
import org.sfedueye.crudwebappsfedueye.api.qrcode.QRPermanentGenerator;
import org.sfedueye.crudwebappsfedueye.api.qrcode.RequestFormByQRCode;
import org.sfedueye.crudwebappsfedueye.api.qrcode.ResponseFormByQRCode;
import org.sfedueye.crudwebappsfedueye.web.data.model.User;
import org.sfedueye.crudwebappsfedueye.web.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseFormByQRCode checkUserAfterScanningQR(@RequestBody RequestFormByQRCode form) {
        User user;

        if(!form.getRandomKey().equals(QRPermanentGenerator.getKey()))
            return new ResponseFormByQRCode(false, "Попробуйте ещё раз");

        if (!repository.existsById(form.getUserId()))
            return new ResponseFormByQRCode(false, "Вас нет в нашей базе данных!");

        user = repository.findUserById(form.getUserId());
        if (form.getDataHashCode() != user.hashCode() || !user.isEnabled())
            return new ResponseFormByQRCode(false, "Данные пользователя не совпадают с данными на сервере или пользователь заблокирован");

        return new ResponseFormByQRCode(true, "Доступ разрешён");

        //TODO(Организовать какое-то действие в случае успеха)
    }


}


