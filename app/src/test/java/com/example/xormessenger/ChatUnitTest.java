package com.example.xormessenger;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class ChatUnitTest {
    @Mock
    Context mockContext;

    @Test
    public void ConstructRequestBody() {
        Chat testChat = new Chat();
        testChat.encryptButton = new ToggleButton(mockContext);
        String testMsg = "";
        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("encrypt", "false");
        testMap.put("message", "");
        testMap.put("user", "");
        assertEquals(testMap, testChat.constructRequestBody(testMsg));
    }

    @Test
    public void NewMessage() {
        Chat testChat = new Chat();
        testChat.layout = new LinearLayout(mockContext);
        testChat.addMessageBox("hello there", 1, false);
        assertEquals(0, testChat.layout.getChildCount());
    }
}
