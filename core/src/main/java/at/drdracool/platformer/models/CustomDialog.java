package at.drdracool.platformer.models;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;


public class CustomDialog extends Dialog {
    int col_width;
    int row_height;
    Skin labelSkin;

    public CustomDialog(String title, Skin skin, int col_width, int row_height, Skin labelSkin) {
        super(title, skin);
        this.col_width = col_width;
        this.row_height = row_height;
        this.labelSkin = labelSkin;
        setup();
    }

    private void setup() {
        padLeft(col_width);
        padRight(col_width);
        padBottom(row_height * 0.5f);
        getButtonTable().defaults().height(row_height);
        getContentTable().defaults().width(col_width * 4);

        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    @Override
    public CustomDialog text(String text) {
        Label label = new Label(text, labelSkin, "c2");
        label.setFontScale(1.2f);
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setWidth(col_width * 2);

        text(label);

        return this;
    }

    public CustomDialog addSmallText(String text) {
        Label label = new Label(text, labelSkin, "c1");
        label.setHeight(row_height);
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setWidth(col_width * 2);


        text(label);

        return this;
    }

    public CustomDialog button(String buttonText, InputListener listener) {
        TextButton button = new TextButton(buttonText, labelSkin, "color2");
        button.setSize(col_width * 2, row_height);
        button.addListener(listener);
        button.padLeft((float) (col_width * 0.5));
        button.padRight((float) (col_width * 0.5));
        button(button);

        return this;

    }

    @Override
    public float getPrefWidth() {
        return col_width * 6;
    }

    @Override
    public float getPrefHeight() {
        return row_height * 4;
    }


}
