package com.ilumer.textpastry;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorldListAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final CaretModel caretModel = editor.getCaretModel();
        Document document = editor.getDocument();

        String inputTxt = Messages.showInputDialog(project, "List of words (space separated)", "WorldList", null);
        if (inputTxt == null) {
            return;
        }
        String[] wordList = inputTxt.split(" ");
        WriteCommandAction.runWriteCommandAction(project, () -> {
            List<Caret> carets = caretModel.getAllCarets();
            for (int i = 0; i < carets.size(); i++) {
                Caret caret = carets.get(i);

                String appendText = "";
                if (i < wordList.length) {
                    appendText = wordList[i];
                }
                int end = caret.getSelectionEnd();
                document.insertString(end, appendText);
                caret.moveToOffset(end + appendText.length());
            }
        });
    }
}
