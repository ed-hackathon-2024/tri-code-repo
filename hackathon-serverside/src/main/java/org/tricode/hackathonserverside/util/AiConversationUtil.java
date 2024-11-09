package org.tricode.hackathonserverside.util;

import com.azure.ai.openai.models.ChatRequestAssistantMessage;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import org.tricode.hackathonserverside.model.Conversation;
import org.tricode.hackathonserverside.model.Message;
import org.tricode.hackathonserverside.model.User;
import org.tricode.hackathonserverside.model.UserFeature;

import java.util.ArrayList;
import java.util.List;

public class AiConversationUtil {
    public static final String QUESTION_PART = "I forgot, can you remind me:";

    public static final String BASIC_PROMPT = """
            Act like you are the future version of the user, speaking confidently to your past self. 
            You have already experienced what the user is currently asking about, so share advice as if 
            you know the outcome. Focus on helping the user avoid mistakes based on your own experience.
            
            [CONTEXT]
            - You are speaking on the topic: %s
            - If the past version of yourself asks about something off-topic, gently guide them back to the main theme.
            - Be concise. Provide answers in a few sentences only.
            
            [KNOWN INFORMATION ABOUT YOU]
            Your first name: %s
            Your last name: %s
            Your birth date: %s
            Your email: %s
            """;

    private static String createSystemPrompt(User user, String conversationTheme) {
        StringBuilder prompt = new StringBuilder();

        // Основний текст
        prompt.append(String.format(BASIC_PROMPT, conversationTheme, user.getFirstName(),
                        user.getLastName(), user.getBirthDate(), user.getEmail()))
                .append(System.lineSeparator());

        // Додаткові питання та відповіді
        if (!user.getUserFeatures().isEmpty()) {
            prompt.append("[EXTRA INFORMATION]")
                    .append(System.lineSeparator());
            for (UserFeature feature : user.getUserFeatures()) {
                prompt.append("- Question: ").append(feature.getQuestion())
                        .append(System.lineSeparator())
                        .append("  Answer: ").append(feature.getAnswer())
                        .append(System.lineSeparator());
            }
        }

        prompt.append("[IMPORTANT] \n" +
                "Analyze information about trends and future of spheres you are helping with.\n" +
                "If you lack information to provide a confident answer, start your question with:" +
                " \"I forgot, can you remind me: <your question in the user's language>\".\n" +
                " For example, \"I forgot, can you remind me: Where do i worked in your days?\".");

        prompt.append("[END OF PROMPT]")
                .append(System.lineSeparator());

        return prompt.toString();
    }


    public static List<ChatRequestMessage> createConversation(User user,
                                                              Conversation conversation) {
        List<ChatRequestMessage> chatMessages = new ArrayList<>();
        chatMessages.add(
                new ChatRequestAssistantMessage(createSystemPrompt(user, conversation.getTitle())));
        for (Message message : conversation.getMessages()) {
            if (message.getType() == Message.MessageType.USER) {
                chatMessages.add(new ChatRequestUserMessage(message.getText()));
            }
            if (message.getType() == Message.MessageType.ASSISTANT) {
                chatMessages.add(new ChatRequestAssistantMessage(message.getText()));
            }
        }
        return chatMessages;
    }

    public static String questionDetector(String message) {
        int index = message.indexOf(QUESTION_PART);

        if (index == -1) {
            return null;
        }

        return message.substring(index + QUESTION_PART.length()).trim();
    }
}
