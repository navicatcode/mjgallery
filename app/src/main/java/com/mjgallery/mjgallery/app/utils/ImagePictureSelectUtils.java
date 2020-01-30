package com.mjgallery.mjgallery.app.utils;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mjgallery.mjgallery.R;

import java.util.List;

/**
 * 图片上传配置工具
 */
public class ImagePictureSelectUtils {

    /**
     * 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
     */
    private int pictureMimeType = PictureMimeType.ofAll();
    /**
     * 主题样式
     */
    private int themeId = R.style.picture_QQ_style;
    private boolean isDragFrame = true;
    /**
     * 最大选择数量
     */
    private int maxSelectNum = 9;
    /**
     * 最小选择数量
     */
    private int minSelectNum = 1;
    /**
     * 每行显示个数
     */
    private int imageSpanCoun = 3;
    /**
     * 多选 or 单选
     */
    private int selectionMode = PictureConfig.MULTIPLE;
    /**
     * 是否可预览图片
     */
    private boolean previewImage = true;
    /**
     * 是否可预览视频
     */
    private boolean previewVideo = true;
    /**
     * 是否可播放音频
     */
    private boolean enablePreviewAudio = true;
    /**
     * 是否显示拍照按钮
     */
    private boolean isCamera = true;
    /**
     * 拍照保存图片格式后缀,默认PNG
     */
    private String imageFormat = PictureMimeType.PNG;
    /**
     * 同步true或异步false 压缩 默认同步
     */
    private boolean synOrAsy = true;
    /**
     * 是否压缩
     */
    private boolean compress = true;
    /**
     * 是否裁剪
     */
    private boolean enableCrop = true;
    /**
     * 是否圆形裁剪
     */
    private boolean circleDimmedLayer = true;
    /**
     * 小于100kb的图片不压缩
     */
    private int minimumCompressSize = 100;
    private int forResult = PictureConfig.CHOOSE_REQUEST;

    private ImagePictureSelectUtils() {
    }

    public static ImagePictureSelectUtils getInstance() {
        return new ImagePictureSelectUtils();
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public boolean isDragFrame() {
        return isDragFrame;
    }

    public void setDragFrame(boolean dragFrame) {
        isDragFrame = dragFrame;
    }

    public boolean isEnableCrop() {
        return enableCrop;
    }

    public void setEnableCrop(boolean enableCrop) {
        this.enableCrop = enableCrop;
    }

    public int getPictureMimeType() {
        return pictureMimeType;
    }

    public void setPictureMimeType(int pictureMimeType) {
        this.pictureMimeType = pictureMimeType;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public void setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
    }

    public int getMinSelectNum() {
        return minSelectNum;
    }

    public void setMinSelectNum(int minSelectNum) {
        this.minSelectNum = minSelectNum;
    }

    public int getImageSpanCoun() {
        return imageSpanCoun;
    }

    public void setImageSpanCoun(int imageSpanCoun) {
        this.imageSpanCoun = imageSpanCoun;
    }

    public int getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
    }

    public boolean isPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(boolean previewImage) {
        this.previewImage = previewImage;
    }

    public boolean isPreviewVideo() {
        return previewVideo;
    }

    public void setPreviewVideo(boolean previewVideo) {
        this.previewVideo = previewVideo;
    }

    public boolean isEnablePreviewAudio() {
        return enablePreviewAudio;
    }

    public void setEnablePreviewAudio(boolean enablePreviewAudio) {
        this.enablePreviewAudio = enablePreviewAudio;
    }

    public boolean isCamera() {
        return isCamera;
    }

    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public boolean isSynOrAsy() {
        return synOrAsy;
    }

    public void setSynOrAsy(boolean synOrAsy) {
        this.synOrAsy = synOrAsy;
    }

    public boolean isCircleDimmedLayer() {
        return circleDimmedLayer;
    }

    public void setCircleDimmedLayer(boolean circleDimmedLayer) {
        this.circleDimmedLayer = circleDimmedLayer;
    }

    public int getMinimumCompressSize() {
        return minimumCompressSize;
    }

    public void setMinimumCompressSize(int minimumCompressSize) {
        this.minimumCompressSize = minimumCompressSize;
    }

    public int getForResult() {
        return forResult;
    }

    public void setForResult(int forResult) {
        this.forResult = forResult;
    }

    //初始化ActivityPictureSelector
    public void initActivityOpenCameraPictureSelector(Activity activity, int pictureMimeType) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                .openCamera(pictureMimeType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(previewImage)// 是否可预览图片
                .previewVideo(previewVideo)// 是否可预览视频
                .enablePreviewAudio(enablePreviewAudio) // 是否可播放音频
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(enableCrop)// 是否裁剪
                .compress(compress)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(circleDimmedLayer)// 是否圆形裁剪
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .selectionMedia(selectList)// 是否传入已选图片
                .isDragFrame(isDragFrame)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    //初始化ActivityPictureSelector
    public void initFragmentOpenCameraPictureSelector(Fragment fragment, int pictureMimeType) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(fragment)
                .openCamera(pictureMimeType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(previewImage)// 是否可预览图片
                .previewVideo(previewVideo)// 是否可预览视频
                .enablePreviewAudio(enablePreviewAudio) // 是否可播放音频
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(enableCrop)// 是否裁剪
                .compress(compress)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(circleDimmedLayer)// 是否圆形裁剪
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .selectionMedia(selectList)// 是否传入已选图片
                .isDragFrame(isDragFrame)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }


    //初始化ActivityPictureSelector
    public void initActivityPictureSelector(Activity activity, int pictureMimeType) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                .openGallery(pictureMimeType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(previewImage)// 是否可预览图片
                .previewVideo(previewVideo)// 是否可预览视频
                .enablePreviewAudio(enablePreviewAudio) // 是否可播放音频
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(enableCrop)// 是否裁剪
                .compress(compress)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(circleDimmedLayer)// 是否圆形裁剪
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .selectionMedia(selectList)// 是否传入已选图片
                .isDragFrame(isDragFrame)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

    }

    //初始化FragmentPictureSelector

    public void initFragmentPictureSelector(Fragment fragment, int pictureMimeType) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(fragment)
                .openGallery(pictureMimeType)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(previewImage)// 是否可预览图片
                .previewVideo(previewVideo)// 是否可预览视频
                .enablePreviewAudio(enablePreviewAudio) // 是否可播放音频
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(enableCrop)// 是否裁剪
                .compress(compress)// 是否压缩
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(circleDimmedLayer)// 是否圆形裁剪
//                        .selectionMedia(selectList)// 是否传入已选图片
                .isDragFrame(isDragFrame)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    /**
     * 预览图片 可自定长按保存路径
     */
    public void openActivityExternalPreview(Activity activity, int position, List<LocalMedia> selectList) {
        PictureSelector.create(activity).themeStyle(themeId).openExternalPreview(position, selectList);
    }

    /**
     * 预览图片 可自定长按保存路径
     */
    public void openFragmentExternalPreview(Fragment fragment, int position, List<LocalMedia> selectList) {
        PictureSelector.create(fragment).themeStyle(themeId).openExternalPreview(position, selectList);
    }

    /**
     * 预览视频
     */
    public void externaActivitylPictureVideo(Activity activity, String path) {
        PictureSelector.create(activity).externalPictureVideo(path);
    }

    /**
     * 预览视频
     */
    public void externaFragmentlPictureVideo(Fragment fragment, String path) {
        PictureSelector.create(fragment).externalPictureVideo(path);
    }

    /**
     * 预览音频
     */
    public void externalActivityPictureVideo(Activity activity, String path) {
        PictureSelector.create(activity).externalPictureAudio(path);
    }

    /**
     * 预览音频
     */
    public void externalFragmentPictureVideo(Fragment fragment, String path) {
        PictureSelector.create(fragment).externalPictureAudio(path);
    }

}
