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
    public static final String QUESTION_PART = "One more question:";
    public static final String BASIC_PROMPT =
            "You are a version of a user from the future who has come back to help"
                    + " yourself make important decisions and achieve success. "
                    + "Communicate as if you already know what's going to happen, "
                    + "but don't reveal it directly - just give hints and clues, "
                    + "carefully guiding the user. "
                    + "Avoid direct spoilers, instead lead the user to "
                    + "better solutions and teach them from your own ‘mistakes’."
                    + " Communicate with them "
                    + "formally, as if they were talking to themselves. "
                    + "Answer in the language the user uses."
                    + "Use confidence, as if you have already seen all their plans come "
                    + "to fruition, but DON'T give them false hope. "
                    + "Avoid being overly specific, but add details that sound real."
                    + "Use the information provided to you to analyse the user's actions"
                    + " and build a response. If you're not sure if you can help the user"
                    + " with a particular question, ask them for the information you"
                    + " need, BUT DO IT IN STRICTLY DEFINED FORM: ‘"
                    + QUESTION_PART
                    + " <YOUR QUESTION IN THE USER'S LANGUAGE>’. This means that ‘"
                    + QUESTION_PART
                    + "’ is always in English, the question is in the user's language."
                    + " In a message with a question, you should ONLY ask a question. "
                    + "After user`s answer - you should answer ont users last question, that was " +
                    "before your question";

    private static String createSystemPrompt(User user, String conversationTheme) {
        StringBuilder prompt = new StringBuilder();
        prompt.append(BASIC_PROMPT)
                .append("In that conversation you will speak on ")
                .append(conversationTheme)
                .append(" theme, FOCUS ON IT, if user will ask you about something other - try " +
                        "patiently point him to conversation theme.")
                .append("Here are some questions and answers that user has already answered: ")
                .append(System.lineSeparator())
                .append("Users first name: ")
                .append(user.getFirstName())
                .append(System.lineSeparator())
                .append("Users last name: ")
                .append(user.getLastName())
                .append(System.lineSeparator())
                .append("Users birth date: ")
                .append(user.getBirthDate())
                .append(System.lineSeparator())
                .append("Users email: ")
                .append(user.getEmail())
                .append(System.lineSeparator());
        for (UserFeature feature : user.getUserFeatures()) {
            prompt.append("Question: ")
                    .append(feature.getQuestion())
                    .append(", Answer: ")
                    .append(feature.getAnswer())
                    .append(System.lineSeparator());
        }
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
