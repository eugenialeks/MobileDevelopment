package ru.mirea.golysheva.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    public static final String ARG_CIPHER = "cipher";
    public static final String ARG_KEY    = "key";

    private final Bundle args;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        this.args = args;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        SystemClock.sleep(1000);

        byte[] cipherText = args.getByteArray(ARG_CIPHER);
        byte[] keyBytes   = args.getByteArray(ARG_KEY);
        SecretKey secret = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");

        return CryptoUtils.decryptMsg(cipherText, secret);
    }
}