# Android高级控件

标签（空格分隔）： 未分类 ProgressBar

---

### 1. ProgressBar（进度条）
ProgressBar的使用步骤：
    a. XML布局文件的编写
    b. 在Java代码中通过`findViewById()`找到对应的`ProgressBar`
    c. 书写逻辑代码
    d. 注册事件
    
---
### 2. WebView（显示网页）

概述：
  ![](http://7xskyl.com1.z0.glb.clouddn.com/webview.png)
  ![](http://7xskyl.com1.z0.glb.clouddn.com/webview_intent.png)
  
 内容：
   ![](http://7xskyl.com1.z0.glb.clouddn.com/webview_content.png)

使用：
    1. 使用`loadUrl()`加载资源
```java
 private void init() {
    //找到元素
    webView = (WebView) findViewById(R.id.webView);
    //1. webView加载本地资源
    //webView.loadUrl("file:///android_asset/setindex.html");

    //2. wenView加载外部资源
    webView.loadUrl(url);
}
```
     
    
    2. 添加网络访问的权限：
  
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
    3. 处理页面导航
    
    使用`new WebViewClient()`方法使网页使用WebView打开
```java
 webView.setWebViewClient(new WebViewClient(){
    /**
     * WebViewClient可以帮助WebView去处理一些页面的控制和请求通知
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        /**
         * return为true时网页在WebView中打开，false时打开默认的浏览器或者第三方浏览器
         */
       // return super.shouldOverrideUrlLoading(view, url);
        view.loadUrl(url);
        return true;
    }
});
```
    4. 在WebView中使用javascript
```java
//启用支持javascript
WebSettings settings = webView.getSettings();
settings.setJavaScriptEnabled(true);
```
    5. 修改`返回`键
    
```java
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    //当点击返回按钮时
    if(keyCode == event.KEYCODE_BACK){
        //判断当前网页是否可以返回到上一步
        if(webView.canGoBack()){
            webView.goBack(); //返回上一页面
            return true;
        }else{
            System.exit(0); //退出程序
        }
    }
    return super.onKeyDown(keyCode, event);
}
```
    6. 判断页面的加载过程
```java
//判断页面的加载过程
webView.setWebChromeClient(new WebChromeClient(){
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        /**
         * newProgress: 1-100之间的整数
         */
        if(newProgress == 100){
            //网页加载完毕
            closeDialog();
        }else{
            //正在加载，打开ProgressDialog打开
            openDialog(newProgress);
        }
    }

    /**
     * 打开对话框
     * @param newProgress
     */
    private void openDialog(int newProgress) {
        if(dialog == null){
            //不为空时，创建ProgressDialog，并设置基本属性
            dialog = new ProgressDialog(MyActivity.this);
            dialog.setTitle("正在加载。。。");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setProgress(newProgress);
            dialog.show();
        }else {
            //刷新进度
            dialog.setProgress(newProgress);
        }
    }
    /**
     * 关闭对话框
     */
    private void closeDialog() {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }

});
```
    7. WebView使用缓存
```java
//设置WebView优先使用缓存加载
WebSettings settings = webView.getSettings();
settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
```





