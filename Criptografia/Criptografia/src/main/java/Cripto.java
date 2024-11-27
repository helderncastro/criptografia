import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cripto {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String senha = "admin";
        String senha2 = hashSHA("Admin");
        String senhaBD = "C1C224B03CD9BC7B6A86D77F5DACE40191766C485CD55DC48CAF9AC873335D6F";

        System.out.println(hashMD5(senha));

        System.out.println(senha2);

        if (senhaBD.equals(senha2)) {
            System.out.println("Mesma Senha");
        } else {
            System.out.println("Não é a mesma senha");
        }

        System.out.println(senhaForte("Admin 123?"));  // erro
        System.out.println(senhaForte("Admin123?"));  // Ok
        validarComplexidade("aDmin123");

    }

    public static boolean senhaForte(String senha) {
        if (senha.length() < 6) return false;
        if (StringUtils.isBlank(senha)) return false;

        boolean achouNumero = false;
        boolean achouMinuscula = false;
        boolean achouMaiuscula = false;
        boolean achouSimbolo = false;

        for (char c: senha.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                return false;
            }

            if (c >= '0' && c <= '9') {
                achouNumero = true;
            } else if ( c >= 'A' && c <= 'Z'  ) {
                achouMaiuscula = true;
            } else if( c >= 'a' && c <= 'z') {
                achouMinuscula = true;
            } else {
                achouSimbolo = true;
            }
        }
        return  achouNumero && achouMinuscula && achouMaiuscula && achouSimbolo;
    }

    public static void validarComplexidade(String valor) {
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,32}$");
        Matcher matcher = pattern.matcher(valor);

        if (matcher.find()) {
            System.out.println("senha válida");
        } else {
            System.out.println("Senha inválida, verifique se ha espaços ou caracteres impróprios");
        }

    }

    public static String hashMD5(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
        return (messageDigest.toString()) ;
    }

    public static String hashSHA(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        return hexString.toString() ;
    }


}
