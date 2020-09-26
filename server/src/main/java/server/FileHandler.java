package server;

import java.io.*;

public class FileHandler {
    private static File dir;

    public static boolean addMessage(String nickname, String message) {
        dir = new File("client/src/main/java/client/clients_History");
        if (!dir.exists()) {
            dir.mkdir();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("client/src/main/java/client/clients_History/history_" + nickname + ".txt", true))) {
                writer.write(message + "\n");
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setMessageForChangeNick(String oldNick, String newNick) {
        try (BufferedReader reader = new BufferedReader( new FileReader("client/src/main/java/client/clients_History/history_" + oldNick + ".txt"))) {
            String str;
            while ((str = reader.readLine()) != null ) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("client/src/main/java/client/clients_History/history_" + newNick + ".txt", true))) {
                    writer.write(str.replace(oldNick, newNick) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("client/src/main/java/client/clients_History/history_" + oldNick + ".txt");
        file.delete();
    }

    public static String getMessageForNick(String nickname) {
        int MAX_NUMBER_MESS = 100;   //максимальное количество последних сообщений истории чата
        StringBuilder sb = new StringBuilder();
        String[] strArr;
        try (BufferedReader reader = new BufferedReader( new FileReader( "client/src/main/java/client/clients_History/history_" + nickname + ".txt" ))) {
            String str;
            while ((str = reader.readLine()) != null ) {
                sb.append(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        strArr = sb.toString().split("\n");
        if(strArr.length > MAX_NUMBER_MESS) {
            sb = new StringBuilder();
            for (int i = (strArr.length - MAX_NUMBER_MESS); i < strArr.length; i++) {
                sb.append(strArr[i] + "\n");
            }
        }
        return sb.toString();
    }


}
