package ApiPackage;

import android.content.Context;

public class RetrofitInteractor {

    private RestClient foRetrofitCMSApiInteractor;

    public APIClass getAPIClass(Context context) {
        if (foRetrofitCMSApiInteractor == null) {
            foRetrofitCMSApiInteractor = new RestClient(context);
        }

        return foRetrofitCMSApiInteractor.getRetrofit().create(APIClass.class);

    }


}
