package com.example.webWiew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URI;

public class MyActivity extends Activity {
    private String url = "http://2014.qq.com/";
    private WebView webView;
    private ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


       /* Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);*/
        
        init();
    }

    private void init() {
        //找到元素
        webView = (WebView) findViewById(R.id.webView);
        //1. webView加载本地资源
        //webView.loadUrl("file:///android_asset/setindex.html");

        //2. wenView加载外部资源
        webView.loadUrl(url);

        //覆盖WebView默认通过第三方或系统浏览器打开网页的行为，是的网页可以在WebView中打开
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

        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //设置WebView优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

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
    }

    //改写手机物理按键的逻辑--返回键逻辑


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
}
