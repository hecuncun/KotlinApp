package com.example.hcc.weatherapp.http;

import com.example.hcc.weatherapp.config.Constants;
import com.example.hcc.weatherapp.data.LoginResponse;
import com.example.hcc.weatherapp.data.SplashImageResponse;
import com.example.hcc.weatherapp.data.TuChongFeedResponse;
import com.example.hcc.weatherapp.data.TuChongWallPagerResponse;
import com.example.hcc.weatherapp.data.VideoResponse;
import com.stx.xhb.core.api.RestApi;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by hecuncun on 2019/8/29
 */
public interface ApiManager {

//   String BASE_URL="http://baobab.kaiyanapp.com/api/";  只有一个BASE_URL的时候写一个

    /**
     * 获取启动图片
     * @return
     */
    @GET("2/welcome-images")
    Call<SplashImageResponse> getSplashImg(@Query("resolution") String resolution, @Query("width") int width, @Query("height") int height);


    /**
     * 图虫推荐
     *
     * @param param
     * @return
     */
    @GET("feed-app")
    Call<TuChongFeedResponse> getFeedApp(@QueryMap Map<String, String> param);


    /**
     * 图虫壁纸
     *
     * @param page
     * @return
     */
    @GET("3/wall-paper/app")
    Call<TuChongWallPagerResponse> getWallPaper(@Query("page") int page);


    /**
     * 开眼推荐视频
     *
     * @param param
     * @return
     */
    @GET("v2/feed")
    Call<VideoResponse> getVideoList(@QueryMap Map<String, String> param);


    /**
     * 登录接口
     * @param loginName
     * @param password
     * @return
     */
    @GET("security/login")
    Call<LoginResponse> loginCall(@Query("loginName") String loginName, @Query("password") String password);




    class ApiFactory {
        public static ApiManager createVideoApi() {
            return RestApi.getInstance().create(Constants.Companion.getVIDEO_API(), ApiManager.class);
        }

        public static ApiManager createTuChongApi() {
            return RestApi.getInstance().create(Constants.Companion.getTUCHONG_API(), ApiManager.class);
        }

        public static ApiManager createZhiHuApi() {
            return RestApi.getInstance().create(Constants.Companion.getZHIHU_API(), ApiManager.class);
        }

        public static ApiManager createTestApi(){
            return RestApi.getInstance().create(Constants.Companion.getTEST_API(),ApiManager.class);
        }
    }
}
