package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.sheshank.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    public static boolean isOnlineMessage(Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnectedOrConnecting();
        } else {
            showToast(context, context.getString(R.string.please_check_internet_connection));
            return false;
        }
    }

    public static void showToast(Context context, String message) {
        if (context != null && message != null && message.length() > 0) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        String expression = "^(?!.{128})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,18}$";//"^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9].+[A-Z]{2,4}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }


}
