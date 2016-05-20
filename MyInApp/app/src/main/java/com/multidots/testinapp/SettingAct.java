package com.multidots.testinapp;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.multidots.testinapp.util.IabHelper;
import com.multidots.testinapp.util.IabResult;
import com.multidots.testinapp.util.Inventory;
import com.multidots.testinapp.util.Purchase;
import com.multidots.testinapp.util.SkuDetails;

import org.json.JSONObject;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc

/**
 * The Class SettingActivity.
 */
public class SettingAct extends Activity {

    private Button buyButton1, buyButton2;
    /**
     * The tv signout.
     */
    TextView tvsetting, /* tvNicknametitle, */
            tvSoundTitle, tvVibrateTitle,
            tvSoundON, tvSoundOFF, tvVibrateOFF, tvVibrateON, /* tvSigninasTitle, */
            tvUsername, tvSignout, tvAbout, tvRemoveAds, tvRateus, tvResetCounters;

    /**
     * The img plus signin.
     */
    ImageView ivProfileImage, imgPlusSignin;

    /**
     * The ll bottom2.
     */
    LinearLayout llBottom, llBottom2;

    /** The edt nickname. */
    // EditText edtNickname;

    /**
     * The ctx.
     */
    Context ctx;

    /**
     * The text name.
     */
    String userImageUrl, textName, device_id;

    /**
     * The accountarrs.
     */
    public String[] accountarrs;

    /**
     * The m account manager.
     */
    AccountManager mAccountManager;

    /**
     * The progress bar1.
     */
    ProgressBar progressBar1;
    Typeface typeCondense;
    JSONObject jobj;

    // in app purchase
    protected static final String SKU = "android.test.purchased";
    private static final String PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlBpxO+VtUuf0UAEz6e1qiIkLME+EkIy5EQEyzSF+xAXuISPRsOw9tRxDg72Z0k/060y6MUYVi03GK28hFOMVpQdjibj6COoK4k0HXJ5NjfrtQGxMCAzg2TyHuIOGQhnQX4VulFGeDoShASpaiuTRcMDV9YSelUND8ggKBna9TdpxFd7CCcnC0IQxL8Kk/UopqEz/C62XlXh0PCPBw0Ae8evX9lIhhWrJ8t18t9CMlF+U2lMMmYZFsxeDzMvpR5wYWYoif03xzpxV4rGqUw0wgkKuS4NT0lwdkBU4YacwvFAb+yek8tL+2xa1DxE8MAvsqdjprZrPg/ByJhwOTmUwZQIDAQAB";
    protected static final int BUY_REQUEST_CODE = 12345;
    private IabHelper buyHelper;
    private Purchase purchase;
    // private Button butUpdate;

    /**
     * The Constant SCOPE.
     */
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        ctx = SettingAct.this;
        buyButton1 = (Button) findViewById(R.id.buyButton1);
        buyButton2 = (Button) findViewById(R.id.buyButton2);

        buyHelper = new IabHelper(this, PUBKEY);

        buyHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                update();
            }
        });

//        if (Utils.getPrefrences1(ctx, Constants.Ads).equals("1")) {
//            tvRemoveAds.setVisibility(View.GONE);
//        }

//        if (Utils.getPrefrences(ctx, Constants.Sound).equals("on")
//                || Utils.getPrefrences(ctx, Constants.Sound).equals(null)) {
//            tvSoundON.setVisibility(View.VISIBLE);
//            tvSoundOFF.setVisibility(View.GONE);
//            Utils.setPrefrences(ctx, Constants.Sound, "on");
//        } else {
//            tvSoundON.setVisibility(View.GONE);
//            tvSoundOFF.setVisibility(View.VISIBLE);
//        }

//        if (Utils.getPrefrences(ctx, Constants.Vibration).equals("on")
//                || Utils.getPrefrences(ctx, Constants.Vibration).equals(null)) {
//            tvVibrateON.setVisibility(View.VISIBLE);
//            tvVibrateOFF.setVisibility(View.GONE);
//            Utils.setPrefrences(ctx, Constants.Vibration, "on");
//        } else {
//            tvVibrateON.setVisibility(View.GONE);
//            tvVibrateOFF.setVisibility(View.VISIBLE);
//        }

        buyButton1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                buyHelper.launchPurchaseFlow(SettingAct.this, SKU,
                        BUY_REQUEST_CODE, new IabHelper.OnIabPurchaseFinishedListener() {
                            @Override
                            public void onIabPurchaseFinished(IabResult result,
                                                              Purchase info) {
                                if (result.isSuccess()) {
                                    Toast.makeText(SettingAct.this,
                                            "Thanks for buying!",
                                            Toast.LENGTH_SHORT).show();
                                    update();
                                }
                            }
                        });
            }
        });
    }

    private void update() {
        ArrayList<String> moreSkus = new ArrayList<String>();
        moreSkus.add(SKU);
        buyHelper.queryInventoryAsync(true, moreSkus,
                new IabHelper.QueryInventoryFinishedListener() {
                    @Override
                    public void onQueryInventoryFinished(IabResult result,
                                                         Inventory inv) {
                        if (result.isSuccess()) {
                            SkuDetails details = inv.getSkuDetails(SKU);
                            // String price = details.getPrice();

                            // TextView tvPrice =
                            // (TextView)SettingActivity.this.findViewById(R.id.tvRemoveAds);
                            // tvPrice.setText(price);

                            purchase = inv.getPurchase(SKU);

                            if (purchase != null) {
                                Toast.makeText(getApplicationContext(), "Success purchased", Toast.LENGTH_SHORT).show();
//                                Utils.setPrefrences(ctx, Constants.Ads, "1");
                            } else {
                                Toast.makeText(getApplicationContext(), "not purchased", Toast.LENGTH_SHORT).show();
//                                Utils.setPrefrences(ctx, Constants.Ads, "0");
                            }
                            Toast.makeText(SettingAct.this,
                                    "Successful got inventory!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SettingAct.this,
                                    "Error getting inventory!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        buyHelper.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        buyHelper.dispose();
    }
}