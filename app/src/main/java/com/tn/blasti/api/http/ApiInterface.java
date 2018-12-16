package com.tn.blasti.api.http;

import com.tn.blasti.api.models.category.Category;
import com.tn.blasti.api.models.menus.MainMenu;
import com.tn.blasti.api.models.menus.SubMenu;
import com.tn.blasti.api.models.posts.post.Post;
import com.tn.blasti.api.models.posts.post.CommentsAndReplies;
import com.tn.blasti.api.models.posts.post.PostDetails;
import com.tn.blasti.api.params.HttpParams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by amine 15/12/2018.
 */

public interface ApiInterface {

    @GET(HttpParams.API_CATEGORIES)
    Call<List<Category>> getCategories(@Query(HttpParams.API_TEXT_PER_PAGE) int pageCount);

    @GET(HttpParams.API_FEATURED_POSTS)
    Call<List<Post>> getFeaturedPosts(@Query(HttpParams.API_TEXT_PAGE) int pageCount);

    @GET(HttpParams.API_RECENT_POSTS)
    Call<List<Post>> getRecentPosts(@Query(HttpParams.API_TEXT_PAGE) int pageCount);

    @GET(HttpParams.API_CATEGORISED_ALL_POST)
    Call<List<Post>> getPostsByCategory(@Query(HttpParams.API_TEXT_PAGE) int pageCount, @Query(HttpParams.API_TEXT_CATEGORIES) int categoryId);

    @GET(HttpParams.API_POST_DETAILS)
    Call<PostDetails> getPostDetails(@Path(HttpParams.API_TEXT_ID) int postId);

    @GET(HttpParams.API_MENUS)
    Call<List<MainMenu>> getMenus();

    @GET(HttpParams.API_SUB_MENUS)
    Call<SubMenu> getSubMenus(@Path(HttpParams.API_TEXT_ID) int subMenuID);

    @GET
    Call<List<CommentsAndReplies>> getCommentsAndReplies(@Url String url, @Query(HttpParams.API_TEXT_PER_PAGE) int pageCount);

    @FormUrlEncoded
    @POST(HttpParams.API_POST_A_COMMENT)
    Call<String> postAComment(@Field(HttpParams.COMMENT_AUTHOR_NAME) String name,
                              @Field(HttpParams.COMMENT_AUTHOR_EMAIL) String email,
                              @Field(HttpParams.COMMENT_CONTENT) String content,
                              @Query(HttpParams.API_POST) int postID);

    @FormUrlEncoded
    @POST(HttpParams.API_POST_A_COMMENT)
    Call<String> postAReply(@Field(HttpParams.COMMENT_AUTHOR_NAME) String name,
                            @Field(HttpParams.COMMENT_AUTHOR_EMAIL) String email,
                            @Field(HttpParams.COMMENT_CONTENT) String content,
                            @Query(HttpParams.API_POST) int postID,
                            @Query(HttpParams.API_PARENT) int commentParentID);


    @GET(HttpParams.API_SEARCHED_POSTS)
    Call<List<Post>> getSearchedPosts(@Query(HttpParams.API_TEXT_PAGE) int pageCount, @Query(HttpParams.API_TEXT_SEARCH) String searchText);

}
