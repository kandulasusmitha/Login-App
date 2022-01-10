package ApiPackage;

import static constants.StaticConstants.BASE_URL;
import static constants.StaticConstants.SHOW_LOG;

import android.content.Context;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Logger;

public class RestClient {

    private String TAG = RestClient.class.getSimpleName();
    private Retrofit mRetrofit;
    private Context mContext;


    public RestClient(Context context) {
        mContext = context;
        this.mRetrofit = getRetrofitBuilder(context).build();
    }


    private Retrofit.Builder getRetrofitBuilder(Context context) {
        mContext = context;
        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        try {
            retrofitBuilder.baseUrl(getBaseUrl(context));
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            retrofitBuilder.client(getOkHttpClientWithTrustAllHosts(context));
        } catch (Exception e) {
            Logger.error(TAG, e.getMessage());
        }
        return retrofitBuilder;
    }


    private String getBaseUrl(Context context) {

        return BASE_URL;
    }


    Retrofit getRetrofit() {
        return this.mRetrofit;
    }

    /**
     * Trust every server - don't check for any certificate
     */
    private OkHttpClient getOkHttpClientWithTrustAllHosts(Context context) throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]
                {
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                return new X509Certificate[]{};
                            }

                            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                //Nothing do
                            }

                            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                                //Nothing do
                            }
                        }
                };

        /** Install the all-trusting trust manager*/
        OkHttpClient okHttpClient = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            if (SHOW_LOG) {
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sc.getSocketFactory(), new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            //Nothing do
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            //Nothing do
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    })
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("origin", "" + BASE_URL.substring(0, BASE_URL.length() - 1))
                                    /*  .addHeader(KEY_APPID, APP_ID)
                                      .addHeader(KEY_TENANTID,TENANT_ID)*/.build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(interceptor)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS)
                    .build();
            return okHttpClient;

        } catch (Exception e) {
            Logger.error(TAG, e.getMessage());
        }

        return okHttpClient;
    }


    public OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            //Nothing do
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            //Nothing do
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return hostname.equalsIgnoreCase(session.getPeerHost());
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
