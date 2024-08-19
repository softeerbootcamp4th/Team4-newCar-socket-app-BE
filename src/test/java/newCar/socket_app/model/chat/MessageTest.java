package newCar.socket_app.model.chat;

import org.junit.jupiter.api.Test;


class MessageTest {
    @Test
    public void genId(){
        Message message = new Message();
        message.generateUniqueId(1L);
        System.out.println(message.getId());
    }
}