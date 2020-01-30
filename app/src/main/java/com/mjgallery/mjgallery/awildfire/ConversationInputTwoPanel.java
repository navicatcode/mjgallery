package com.mjgallery.mjgallery.awildfire;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jess.arms.utils.ArmsUtils;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.emoji.ViewPagerFixed;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;

import java.nio.channels.SocketChannel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


@SuppressWarnings("all")
public class ConversationInputTwoPanel extends FrameLayout implements IEmotionSelectedListener {
    private static final int TYPING_INTERVAL_IN_SECOND = 10;
    private static SocketChannel sSocketChannel;
    @BindView(R.id.audioImageView)
    ImageView audioImageView;
    @BindView(R.id.audioButton)
    Button audioButton;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.emotionImageView)
    ImageView emotionImageView;
    @BindView(R.id.extImageView)
    ImageView extImageView;
    @BindView(R.id.sendButton)
    RelativeLayout sendButton;
    @BindView(R.id.emotionContainerFrameLayout)
    KeyboardHeightFrameLayout emotionContainerFrameLayout;
    @BindView(R.id.emotionLayout)
    EmotionLayout emotionLayout;
    @BindView(R.id.extContainerContainerLayout)
    KeyboardHeightFrameLayout extContainerFrameLayout;
    @Nullable
    @BindView(R.id.inputPanelFrameLayout)
    FrameLayout inputContainerFrameLayout;
    @BindView(R.id.conversationExtViewPager)
    ViewPagerFixed extViewPager;
    private InputAwareLayout rootLinearLayout;
    private AppCompatActivity activity;
    private long lastTypingTime;
    private String draftString;
    private SharedPreferences sharedPreferences;
    private long userId;
    private String username;
    private long roomId;
    private int wx_access = 2;
    private String headImg;

    private OnConversationInputPanelStateChangeListener onConversationInputPanelStateChangeListener;

    public ConversationInputTwoPanel(@NonNull Context context) {
        super(context);
    }

    public ConversationInputTwoPanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ConversationInputTwoPanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ConversationInputTwoPanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void init(AppCompatActivity activity, InputAwareLayout rootInputAwareLayout) {
        LayoutInflater.from(getContext()).inflate(R.layout.conversation_input_panel, this, true);
        ButterKnife.bind(this, this);
        this.activity = activity;
        this.rootLinearLayout = rootInputAwareLayout;

//        this.extension = new ConversationExtension(activity, this, extViewPager);


        sharedPreferences = getContext().getSharedPreferences("sticker", Context.MODE_PRIVATE);

        // emotion
        emotionLayout.attachEditText(editText);
        emotionLayout.setEmotionAddVisiable(true);
        emotionLayout.setEmotionSettingVisiable(true);

        editText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                Editable buffer = ((EditText) v).getText();
                // If the cursor is at the end of a MentionSpan then remove the whole span
                int start = Selection.getSelectionStart(buffer);
                int end = Selection.getSelectionEnd(buffer);
                if (start == end) {
                    MentionSpan[] mentions = buffer.getSpans(start, end, MentionSpan.class);
                    if (mentions.length > 0) {
                        buffer.replace(
                                buffer.getSpanStart(mentions[0]),
                                buffer.getSpanEnd(mentions[0]),
                                ""
                        );
                        buffer.removeSpan(mentions[0]);
                        return true;
                    }
                }
                return false;
            }
            return false;
        });

//        // audio record panel
//        audioRecorderPanel = new AudioRecorderPanel(getContext());
//        audioRecorderPanel.setRecordListener(new AudioRecorderPanel.OnRecordListener() {
//            @Override
//            public void onRecordSuccess(String audioFile, int duration) {
//                //发送文件
//                File file = new File(audioFile);
//                if (file.exists()) {
//                    conversationViewModel.sendAudioFile(Uri.parse(audioFile), duration);
//                }
//            }
//
//            @Override
//            public void onRecordFail(String reason) {
//                Toast.makeText(activity, reason, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRecordStateChanged(AudioRecorderPanel.RecordState state) {
//                if (state == AudioRecorderPanel.RecordState.START) {
//                    TypingMessageContent content = new TypingMessageContent(TypingMessageContent.TYPING_VOICE);
//                    conversationViewModel.sendMessage(content);
//                }
//            }
//        });

        // emotion
        emotionLayout.setEmotionSelectedListener(this);
        emotionLayout.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                ArmsUtils.makeText(activity, "add");
            }

            @Override
            public void onEmotionSettingClick(View view) {
                ArmsUtils.makeText(activity, "setting");
            }
        });

    }

    @OnClick(R.id.extImageView)
    void onExtImageViewClick() {
        if (rootLinearLayout.getCurrentInput() == extContainerFrameLayout) {
            rootLinearLayout.showSoftkey(editText);
            showEmotionLayout();
            hideConversationExtension();

        } else {
            emotionImageView.setImageResource(R.drawable.item_biao_qing);
            showConversationExtension();
        }
    }

    @OnClick(R.id.emotionImageView)
    void onEmotionImageViewClick() {
        if (rootLinearLayout.getCurrentInput() == emotionContainerFrameLayout) {
            showConversationExtension();
            hideEmotionLayout();

        } else {
            showEmotionLayout();
            hideAudioButton();

        }
    }

    @OnTextChanged(value = R.id.editText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterInputTextChanged(Editable editable) {
        Log.i("发送表情", "afterInputTextChanged" + editText.getText().toString().trim());
        if (editText.getText().toString().trim().length() > 0) {
            if (activity.getCurrentFocus() == editText) {

            }
            extImageView.setVisibility(View.GONE);
        } else {
            extImageView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.audioImageView})
    public void showRecordPanel() {
        if (audioButton.isShown()) {
            editText.requestFocus();
            rootLinearLayout.showSoftkey(editText);
            hideAudioButton();

        } else {
//            editText.clearFocus();
            showAudioButton();
            hideEmotionLayout();
            rootLinearLayout.hideSoftkey(editText, null);
            hideConversationExtension();
        }
    }


    /**
     * 发送文本内容
     */
    @OnClick({R.id.sendButton})
    public void sendClick() {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            ArmsUtils.makeText(getContext(), ArmsUtils.getString(BaseApplication.getInstance(),R.string.please_ente_comment));
            return;
        }
        if (onConversationInputPanelStateChangeListener != null) {
            onConversationInputPanelStateChangeListener.onSendText(editText.getText().toString().trim());
        }
    }

    public void show(InputAwareLayout rootInputAwareLayout) {
        editText.requestFocus();
        rootInputAwareLayout.showSoftkey(editText);

    }

    public void onKeyboardShown() {
        hideEmotionLayout();
    }

    public void onKeyboardHidden() {
        // do nothing
    }

    private void showAudioButton() {
        audioButton.setVisibility(View.VISIBLE);
//        audioRecorderPanel.attach(rootLinearLayout, audioButton);
        editText.setVisibility(View.GONE);
        audioImageView.setImageResource(R.drawable.ic_cheat_keyboard);
        rootLinearLayout.hideCurrentInput(editText);
        rootLinearLayout.hideAttachedInput(true);
    }

    private void hideAudioButton() {
        audioButton.setVisibility(View.GONE);
//        audioRecorderPanel.deattch();
        editText.setVisibility(View.VISIBLE);
        rootLinearLayout.show(editText, emotionContainerFrameLayout);
        audioImageView.setImageResource(R.drawable.ic_cheat_voice);
    }

    private void showEmotionLayout() {
        audioButton.setVisibility(View.GONE);
        emotionImageView.setImageResource(R.drawable.ic_cheat_keyboard);
        if (onConversationInputPanelStateChangeListener != null) {
            onConversationInputPanelStateChangeListener.onInputPanelExpanded();
        }
    }

    private void hideEmotionLayout() {
        emotionImageView.setImageResource(R.drawable.item_biao_qing);
        if (onConversationInputPanelStateChangeListener != null) {
            onConversationInputPanelStateChangeListener.onInputPanelCollapsed();
        }
    }

    private void showConversationExtension() {
        editText.requestFocus();
        rootLinearLayout.showSoftkey(editText);
        rootLinearLayout.show(editText, extContainerFrameLayout);
        if (audioButton.isShown()) {
            hideAudioButton();
        }
        if (onConversationInputPanelStateChangeListener != null) {
            onConversationInputPanelStateChangeListener.onInputPanelExpanded();
        }
    }

    private void hideConversationExtension() {
        if (onConversationInputPanelStateChangeListener != null) {
            onConversationInputPanelStateChangeListener.onInputPanelCollapsed();
        }
    }

    public void collapse() {
        emotionImageView.setImageResource(R.drawable.item_biao_qing);
        rootLinearLayout.hideAttachedInput(true);
        rootLinearLayout.hideCurrentInput(editText);
    }

    @Override
    public void onEmojiSelected(String key) {
        Log.i("发送表情", "onEmojiSelected key=" + key);
    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
        String remoteUrl = sharedPreferences.getString(stickerBitmapPath, null);
        Log.i("发送表情", "onStickerSelected stickerBitmapPath=" + stickerBitmapPath + " stickerName=" + stickerName + " categoryName=" + categoryName);
    }

    public void setOnConversationInputPanelStateChangeListener(OnConversationInputPanelStateChangeListener onConversationInputPanelStateChangeListener) {
        this.onConversationInputPanelStateChangeListener = onConversationInputPanelStateChangeListener;
    }


    public interface ISendClickListener {
        void onSendClickListener(String sendText);
    }

    public interface OnConversationInputPanelStateChangeListener {
        //输入面板展开
        void onInputPanelExpanded();

        //输入面板关闭
        void onInputPanelCollapsed();

        void onSendText(String sendText);
    }
}
