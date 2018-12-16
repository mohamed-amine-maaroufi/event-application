package com.tn.blasti.data.constant;

/**
 * Created by amine 15/12/2018.
 */

public class AppConstant {

    public static final String EMPTY = " ";

    public static final String BUNDLE_KEY_TITLE = "title";
    public static final String BUNDLE_KEY_MESSAGE = "message";
    public static final String BUNDLE_KEY_URL = "url";
    public static final String BUNDLE_FROM_PUSH = "from_push";
    public static final String BUNDLE_FROM_APP_LINK = "from_app_link";
    public static final long SITE_CACHE_SIZE = 10 * 1024 * 1024;
    public static final String BUNDLE_KEY_POST_ID = "post_id";
    public static final String BUNDLE_KEY_POST = "post";
    public static final String BUNDLE_KEY_CATEGORY_ID = "category_id";
    public static final String BUNDLE_KEY_CATEGORY_TITLE = "category_title";
    public static final String BUNDLE_KEY_FEATURED = "isFromFeatured";
    public static final String BUNDLE_KEY_CATEGORY_LIST = "category_list";
    public static final String BUNDLE_KEY_SUB_CATEGORY_LIST = "sub_category_list";
    public static final String BUNDLE_KEY_MENU_ID = "post_id";
    public static final String BUNDLE_KEY_SUB_MENU = "sub_menu";
    public static final String BUNDLE_KEY_ALL_COMMENT = "all_comment";
    public static final String BUNDLE_KEY_ALL_ZERO_PARENT_COMMENT = "zero_parent_comments";
    public static final String BUNDLE_KEY_CLICKED_COMMENT = "clicked_comment";
    public static final String BUNDLE_KEY_DIALOG_FRAGMENT = "dialog_fragment";
    public static final String BUNDLE_KEY_SHOULD_DIALOG_OPEN = "should_dialog_open";
    public static final String BUNDLE_KEY_SEARCH_TEXT = "search_text";
    public static final int THIS_IS_COMMENT = -1;
    public static final int REQUEST_CODE_COMMENT = 0;
    public static final String BUNDLE_KEY_COMMENT_STATUS = "comment_success_status";
    public static final int DEFAULT_CATEGORY_ID = -1;
    public static final int MAX_VALUE = 999;
    public static final String NEW_NOTI = "new_notification";
    public static final String DOT = ".";

    public static final String ARG_CLICKED_POST_ID = "clicked_post_id";
    public static final String ARG_COMMENT_ID = "comment_id";
    public static final String COMMENT_MESSAGE = "message";
    public static final String COMMENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final Double COMMENT_ID = 1000.0;
    public static final Double COMMENT_PARENT_ID = 0.0;

    public static final int DEFAULT_PAGE = 1;


    // replace call number, sms number and email address by yours
    public static final String CALL_NUMBER = "+21646606060"; // replace by your support number

    public static final String SMS_NUMBER = "+21646606060"; // replace by your support sms number
    public static final String SMS_TEXT = "Envoyez vos avis pour améliorer nos services..."; // replace by your message

    public static final String EMAIL_ADDRESS = "contact@blasti.net"; // replace by your support sms number
    public static final String EMAIL_SUBJECT = "Feedback"; // replace by your message
    public static final String EMAIL_BODY = "Envoyez vos avis pour améliorer nos services..."; // replace by your message


    // Menu item type
    public static final String MENU_ITEM_CATEGORY = "categorie";
    public static final String MENU_ITEM_PAGE = "page";
    public static final String MENU_ITEM_CUSTOM = "custom";
    public static final String MENU_ITEM_POST = "post";

    public static final String CSS_PROPERTIES = "<style>body{width:100%;margin:0;}img {max-width:100%;height:auto;} iframe{width:100%;}</style>";

}
