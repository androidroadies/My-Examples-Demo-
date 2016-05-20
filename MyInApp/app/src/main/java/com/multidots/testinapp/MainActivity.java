package com.multidots.testinapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.multidots.testinapp.util.IabHelper;
import com.multidots.testinapp.util.IabResult;
import com.multidots.testinapp.util.Inventory;
import com.multidots.testinapp.util.Purchase;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button clickButton;
    private static Button buyButton1;
    private static Button buyButton2;

    private Purchase purchase;
    public static boolean item1 = false;
    public static boolean item2 = false;

    static final String ITEM_SKU1 = "android.test.purchased";
    static final String ITEM_SKU2 = "android.test.purchased";
    private static final String TAG = "Inapp";
    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlBpxO+VtUuf0UAEz6e1qiIkLME+EkIy5EQEyzSF+xAXuISPRsOw9tRxDg72Z0k/060y6MUYVi03GK28hFOMVpQdjibj6COoK4k0HXJ5NjfrtQGxMCAzg2TyHuIOGQhnQX4VulFGeDoShASpaiuTRcMDV9YSelUND8ggKBna9TdpxFd7CCcnC0IQxL8Kk/UopqEz/C62XlXh0PCPBw0Ae8evX9lIhhWrJ8t18t9CMlF+U2lMMmYZFsxeDzMvpR5wYWYoif03xzpxV4rGqUw0wgkKuS4NT0lwdkBU4YacwvFAb+yek8tL+2xa1DxE8MAvsqdjprZrPg/ByJhwOTmUwZQIDAQAB";
    IabHelper mHelper;
    IInAppBillingService mService;

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            checkPurchase();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        buyButton1 = (Button) findViewById(R.id.buyButton1);
        buyButton2 = (Button) findViewById(R.id.buyButton2);
        clickButton = (Button) findViewById(R.id.clickButton);
        clickButton.setEnabled(false);

        buyButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyClick1();
            }
        });

        buyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyClick2();
            }
        });

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });
    }

    private void checkPurchase() {
        ArrayList<String> skuList = new ArrayList<String>();
        skuList.add(ITEM_SKU1);
        skuList.add(ITEM_SKU2);
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
        try {
            Bundle skuDetails = mService.getPurchases(3, getPackageName(), "inapp", null);

            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");

                for (String thisResponse : responseList) {
                    String sku = thisResponse;
                    if (sku.equals(ITEM_SKU1)) item1 = true;
                    else if (sku.equals(ITEM_SKU2)) item2 = true;
                }
                Toast.makeText(getApplicationContext(), "item1:" + item1, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "item2:" + item2, Toast.LENGTH_SHORT).show();
            }
            setButtonState();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setButtonState() {
        if (item1) {
            buyButton1.setEnabled(false);
        } else if (item2) {
            buyButton2.setEnabled(false);
        }
    }

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            if (result.isFailure()) {
                // Handle failure
                Toast.makeText(MainActivity.this,
                        "Purchase failed!",
                        Toast.LENGTH_SHORT).show();
            } else if (result.isSuccess()) {
                // success payment here
                purchase = inventory.getPurchase(ITEM_SKU1);
                if (purchase != null) {
                    Log.e(TAG, "Purchase success item1!");
                    item1 = true;
                } else {
                    Log.e(TAG, "Purchase fail item1!");
                    item1 = false;
                }

                purchase = inventory.getPurchase(ITEM_SKU2);
                if (purchase != null) {
                    Log.e(TAG, "Purchase success item2!");
                    item2 = true;
                } else {
                    Log.e(TAG, "Purchase fail item2!");
                    item2 = false;
                }


//                if (inventory.hasPurchase(ITEM_SKU1)) {
//                    purchase = inventory.getPurchase(ITEM_SKU1);
//                    if (purchase != null) {
//                        item1 = true;
////                        mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU1),
////                                mConsumeFinishedListener);
//                    } else {
//
//                    }
//                } else if (inventory.hasPurchase(ITEM_SKU2)) {
//                    purchase = inventory.getPurchase(ITEM_SKU2);
//
//                    if (purchase != null) {
//                        item2 = true;
////                        mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU2),
////                                mConsumeFinishedListener);
//                    } else {
//
//                    }
//                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {
                    if (result.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Item Consumed", Toast.LENGTH_SHORT).show();
                        clickButton.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), "Item Consumed fail: " + result.getMessage(), Toast.LENGTH_SHORT).show();
                        // handle error
                    }
                }
            };

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                return;
            } else if (purchase.getSku().equals(ITEM_SKU1)) {
                consumeItem();
                buyButton1.setEnabled(false);
            } else if (purchase.getSku().equals(ITEM_SKU2)) {
                consumeItem();
                buyButton2.setEnabled(false);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;

        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

//    public void buttonClicked(View view) {
//        clickButton.setEnabled(false);
//        buyButton1.setEnabled(true);
//        buyButton2.setEnabled(true);
//    }

    public void buyClick1() {
//        if (!item1)
        mHelper.launchPurchaseFlow(this, ITEM_SKU1, 10001,
                mPurchaseFinishedListener, "Item1");
    }

    public void buyClick2() {
//        if (!item2)
        mHelper.launchPurchaseFlow(this, ITEM_SKU2, 10001,
                mPurchaseFinishedListener, "Item2");
    }

//    public static boolean verifyPurchase(String base64PublicKey,
//                                         String signedData, String signature) {
//        if (TextUtils.isEmpty(signedData) ||
//                TextUtils.isEmpty(base64PublicKey) ||
//                TextUtils.isEmpty(signature)) {
//            Log.e(TAG, "Purchase verification failed: missing data.");
//            if (BuildConfig.DEBUG) {
//                return true;
//            }
//            return false;
//        }
//
//        PublicKey key = Security
//                .generatePublicKey(base64PublicKey);
//        return Security.verify(key, signedData, signature);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.print("279 pause");

    }
}