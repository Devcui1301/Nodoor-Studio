package com.nodoor.module;

import com.nodoor.module.admob.Admob;
import com.nodoor.module.admob.AppOpenManager;
import com.nodoor.module.ads.GamAd;
import com.nodoor.module.application.AdsMultiDexApplication;
import com.nodoor.module.billing.AppPurchase;
import com.nodoor.module.config.AdjustConfig;
import com.nodoor.module.config.GamAdConfig;

import java.util.ArrayList;
import java.util.List;

public class App extends AdsMultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initAds();
        initBilling();
    }

    private void initAds() {
        String environment = BuildConfig.DEBUG ? GamAdConfig.ENVIRONMENT_DEVELOP : GamAdConfig.ENVIRONMENT_PRODUCTION;
        mGamAdConfig = new GamAdConfig(this, environment);

        AdjustConfig adjustConfig = new AdjustConfig(true, getString(R.string.adjust_token));
        mGamAdConfig.setAdjustConfig(adjustConfig);
        mGamAdConfig.setFacebookClientToken(getString(R.string.facebook_client_token));
        mGamAdConfig.setAdjustTokenTiktok(getString(R.string.tiktok_token));

        mGamAdConfig.setIdAdResume("");

        GamAd.getInstance().init(this, mGamAdConfig);
        Admob.getInstance().setDisableAdResumeWhenClickAds(true);
        Admob.getInstance().setOpenActivityAfterShowInterAds(true);
        AppOpenManager.getInstance().disableAppResumeWithActivity(MainActivity.class);
    }

    private void initBilling(){
        List<String> listIAP = new ArrayList<>();
        listIAP.add("android.test.purchased");
        List<String> listSub = new ArrayList<>();
        AppPurchase.getInstance().initBilling(this, listIAP, listSub);
    }
}
