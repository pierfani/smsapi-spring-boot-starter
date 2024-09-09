# SMSAPI Spring Boot Starter

[English version below](#english-version)

## Versione Italiana

Questa libreria Spring Boot fornisce un'integrazione semplice e robusta con i servizi [SMSAPI](https://www.smsapi.com), permettendo agli sviluppatori di incorporare facilmente funzionalità di messaggistica SMS nelle loro applicazioni Spring Boot.

### Servizi SMSAPI integrati

Attualmente, la libreria supporta i seguenti servizi:

- [SMS Authenticator](https://www.smsapi.com/docs/#15-sms-authenticator): Implementazione di autenticazione a due fattori tramite SMS.

### Come integrare

1. Aggiungi la dipendenza al tuo `pom.xml`:

    ```xml
    <dependency>
        <groupId>it.pierfani.smsapi</groupId>
        <artifactId>smsapi-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
    ```

2. Configura le proprietà SMSAPI nel tuo application.properties o application.yml:

    ```yaml
    smsapi:
        oauth-token: il_tuo_token_oauth
    ```

3. Inietta e utilizza il servizio nella tua applicazione

### Esempio di utilizzo
Ecco un esempio completo di utilizzo dei metodi sendCode e verifyCode della libreria SMSAPI Spring Boot Starter. Questo esempio mostra come inviare un codice di autenticazione via SMS a un numero di telefono e poi verificare se il codice inserito dall'utente è corretto.

#### Scenario

Supponiamo di avere un'applicazione Spring Boot in cui gli utenti possono autenticarsi utilizzando un codice di verifica inviato via SMS. Il processo avrà due fasi principali:

1. Invio del codice di verifica al numero di telefono dell'utente.
2. Verifica del codice inserito dall'utente.

#### Implementazione

Per illustrare l'implementazione, creiamo un servizio in Spring Boot che gestisce queste due operazioni.

```java
package it.pierfani.smsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private SmsAuthenticatorService smsAuthenticatorService;

    /**
     * Invia un SMS con un codice di autenticazione all'utente.
     * @param phoneNumber Numero di telefono dell'utente.
     * @return Il codice di autenticazione inviato.
     */
    public String sendAuthenticationCode(String phoneNumber) {
        CodeSendingRequest request = new CodeSendingRequest();
        request.setPhoneNumber(phoneNumber);
        request.setContent("Il tuo codice di autenticazione è [%code%].");  // [%code%] verrà sostituito automaticamente dal servizio SMSAPI.

        CodeSendingResponse response = smsAuthenticatorService.sendCode(request);

        // Restituisce il codice di autenticazione inviato.
        return response.getCode();
    }

    /**
     * Verifica il codice di autenticazione inviato all'utente.
     * @param code Codice che l'utente ha inserito.
     * @param phoneNumber Numero di telefono dell'utente.
     * @return true se il codice è corretto, false altrimenti.
     */
    public boolean verifyAuthenticationCode(String code, String phoneNumber) {
        CodeVerificationRequest request = new CodeVerificationRequest();
        request.setCode(code);
        request.setPhoneNumber(phoneNumber);

        return smsAuthenticatorService.verifyCode(request);
    }
}
````

#### Utilizzo in un Controller

Ecco come potresti usare il servizio AuthenticationService in un controller Spring Boot per gestire le richieste di autenticazione:

```java
package it.pierfani.smsapi.controller;

import it.pierfani.smsapi.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestParam String phoneNumber) {
        String authCode = authenticationService.sendAuthenticationCode(phoneNumber);
        return ResponseEntity.ok("Codice inviato con successo: " + authCode);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestParam String code, @RequestParam String phoneNumber) {
        boolean isValid = authenticationService.verifyAuthenticationCode(code, phoneNumber);
        if (isValid) {
            return ResponseEntity.ok("Il codice di verifica è corretto.");
        } else {
            return ResponseEntity.badRequest().body("Codice di verifica errato o scaduto.");
        }
    }
}
```
### Spiegazione

sendAuthenticationCode: Questo metodo invia un codice di autenticazione SMS a un numero di telefono e restituisce il codice di autenticazione inviato tramite CodeSendingResponse. Nella risposta, utilizziamo response.getCode() per ottenere il codice che è stato generato e inviato.
verifyAuthenticationCode: Questo metodo verifica se il codice fornito dall'utente corrisponde al codice inviato. Se il codice è corretto, restituisce true, altrimenti false.
AuthenticationController: Espone due endpoint: /send-code per inviare il codice e /verify-code per verificarlo. La risposta include il codice inviato come conferma.

### Esempio di Richiesta

Ecco un esempio di come potresti invocare questi endpoint:

1. Invia il codice:
    - URL: `POST /auth/send-code?phoneNumber=+391234567890`
    - Risposta: `Codice inviato con successo: 1234`

2. Verifica il codice:
    - URL: `POST /auth/verify-code?code=1234&phoneNumber=+391234567890`
    - Risposta: `Il codice di verifica è corretto.` oppure `Codice di verifica errato o scaduto.`
### Documentazione ufficiale SMS API

Per ulteriori dettagli sulle API SMSAPI, consulta la documentazione ufficiale [SMSAPI](https://www.smsapi.com/docs).

## English Version

This Spring Boot starter library provides a simple and robust integration with [SMSAPI](https://www.smsapi.com) services, allowing developers to easily incorporate SMS messaging capabilities into their Spring Boot applications.

### Integrated SMSAPI Services

Currently, the library supports the following service:

- [SMS Authenticator](https://www.smsapi.com/docs/#15-sms-authenticator): Implementation of two-factor authentication via SMS.

### How to Integrate

1. Add the dependency to your pom.xml:
    ```xml
    <dependency>
        <groupId>it.pierfani.smsapi</groupId>
        <artifactId>smsapi-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
    ```
2. Configure SMSAPI properties in your application.properties or application.yml:

    ```yaml
    smsapi:
        oauth-token: il_tuo_token_oauth
    ```

3. Inject and use the service in your application

### Example
....

### Official SMSAPI documentation

For more details on SMSAPI APIs, please refer to the official [SMSAPI documentation](https://www.smsapi.com/docs) .