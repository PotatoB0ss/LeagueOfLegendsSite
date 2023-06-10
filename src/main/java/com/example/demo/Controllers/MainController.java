package com.example.demo.Controllers;


import com.example.demo.accounts.*;
import com.example.demo.email.EmailSender;
import com.example.demo.login.AutheticationChecker;
import com.example.demo.mmrCheck.CheckMMR;
import com.example.demo.mmrCheck.DataMMR;
import com.example.demo.purchase.*;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    AutheticationChecker autheticationChecker = new AutheticationChecker();
    private final AccountService accountService;
    private final PaymentDataService paymentDataService;
    private final EmailSender emailSender;

    public MainController(AccountService accountService, PaymentDataService paymentDataService, EmailSender emailSender) {
        this.accountService = accountService;
        this.paymentDataService = paymentDataService;
        this.emailSender = emailSender;
    }

    @GetMapping(path = "/")
    public String basePage(Model model) {
        autheticationChecker.authenticationCheck(model);
        accountService.getAccountsCount(model);
        return "main";
    }

    @GetMapping(path="/register")
    public String register(Model model){
        if (autheticationChecker.authenticationCheck(model)) {
            return "redirect:/";
        }
        return "register";
    }

    @GetMapping(path="/mb")
    public String miniBuy(Model model, @RequestParam("productNumber") String productName){
        autheticationChecker.authenticationCheck(model);
        ProductChecker productChecker = new ProductChecker(accountService);
        productChecker.productCheck(model, productName);
        return "modalBuy";
    }

    @GetMapping(path = "/login")
    public String login(Model model){
        if (autheticationChecker.authenticationCheck(model)) {
            return "redirect:/";
        }
        return "loggin";
    }

    @GetMapping(path = "/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/";
    }

    @GetMapping(path ="reset")
    public String reset(Model model){
        if (autheticationChecker.authenticationCheck(model)) {
            return "redirect:/";
        }
        return "passwordReset/passwordRecovery";
    }

    @GetMapping(path = "/mmrCheck")
    public String checkMmr(Model model){
        autheticationChecker.authenticationCheck(model);
        return "mmrChecker";
    }

    @GetMapping(path = "/admin")
    public String adminOpen(Model model){
        autheticationChecker.authenticationCheck(model);
        return "admin";
    }

    @PostMapping(path ="/addAccount")
    public String addAccount(Model model, @ModelAttribute Account account){
        autheticationChecker.authenticationCheck(model);
        accountService.save(account);
        return "admin";
    }

    @PostMapping("/mmrChecks")
    @ResponseBody
    public Map<String, Object> checkMmrRequest(Model model, @RequestBody DataMMR dataMMR){
        autheticationChecker.authenticationCheck(model);
        Map<String, Object> response = new HashMap<>();

        CheckMMR checkMMR = new CheckMMR();
        response.put("uss", checkMMR.inputData(dataMMR.getUsername(), dataMMR.getRegion(), response));

        return response;

    }

    @PostMapping("/accountData")
    @ResponseBody
    public Map<String, Object> getAccountData(Model model){
        autheticationChecker.authenticationCheck(model);
        Map<String, Object> accountData = new HashMap<>();
        accountService.getAccountsCount(model);

        accountData.put("model", model);

        return accountData;

    }

    @PostMapping("/purchaseDetails")
    public String purchaseDetails(Model model, @ModelAttribute Purchase purchase){
        autheticationChecker.authenticationCheck(model);
        Map<String, Object> purchaseData = new HashMap<>();
        purchaseData.put("username", purchase.getUserName());
        purchaseData.put("email", purchase.getUserEmail());
        purchaseData.put("amount", purchase.getNumberInput());
        purchaseData.put("stock", purchase.getStock());
        purchaseData.put("prodPrice", purchase.getProdPrice());
        purchaseData.put("prodName", purchase.getProdName());

        PurchaseDataValidate purchaseDataValidate = new PurchaseDataValidate();
        String result = purchaseDataValidate.dataCheck(purchaseData);


        if(result.equals("successfully")){
            PurchaseRequest request = new PurchaseRequest(paymentDataService);
            String url = request.sendBuyRequest(purchaseData);
            return "redirect:" + url;
        }
        return "redirect:/";
    }

    @PostMapping("/successfulPayment")
    public String checkPurchaseStatus(Model model, @RequestBody SuccessfulPayment successfulPayment, @RequestHeader("HTTP_SIGNATURE") String sign) {
        autheticationChecker.authenticationCheck(model);
        String sign2 = calculateSignature(successfulPayment);
        System.out.println(sign);
        System.out.println(sign2);

        if (sign.equals(sign2)) {
            if(successfulPayment.getStatus().equals("PAID")){
                AdditionalData encodedData = successfulPayment.getData();
                String productName = StringEscapeUtils.unescapeHtml4(encodedData.getProdName());
                String email = StringEscapeUtils.unescapeHtml4(encodedData.getUserEmail());
                Integer quantity = Integer.valueOf(StringEscapeUtils.unescapeHtml4(encodedData.getQuantity()));
                String[] parsedValues = productName.split(" \\| ");
                String accountRegion = parsedValues[0];
                String accountStatus = parsedValues[1];
                System.out.println(accountRegion);
                System.out.println(accountStatus);
                System.out.println(email);
                System.out.println(quantity);
                List<Account> accounts = accountService.getAccounts(AccountRegion.RU, AccountCategory.ACTIVE, PageRequest.of(0, quantity)).getContent();
                String resultAccounts = "http://localhost/productGive?order_id=" + successfulPayment.getOrder_id();
                emailSender.send("drafasenos317@gmail.com", buildEmailPayment("Dear Customer", resultAccounts));
                accountService.setAccountsOrder(accounts, Long.valueOf(successfulPayment.getOrder_id()));
            }
            System.out.println("Successful payment");
            return "redirect:/";
        } else {
            return "redirect:/error";
        }


    }

    @GetMapping("/productGive")
    public String productsGive(Model model, @RequestParam("order_id") Integer order_id){
        autheticationChecker.authenticationCheck(model);
        model.addAttribute("accounts", accountService.findAccountByOrderId(Long.valueOf(order_id)));
        return "accounts";
    }


    private String calculateSignature(SuccessfulPayment successfulPayment) {
        final String token = "3d0614c9abf6e817f9cda5a8ea0b2056";
        String payload = successfulPayment.getId() + "|" + successfulPayment.getCreatedDateTime() + "|" + successfulPayment.getAmount();
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(token.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] hmacBytes = sha256Hmac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(hmacBytes).toLowerCase();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String buildEmailPayment(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Your order</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Hello. This is list of your accounts: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Check Now</a> </p></blockquote>\n Thank you for your purchase. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
