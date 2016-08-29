package com.feximin.box;

import com.feximin.library.R;

/**
 * Created by Neo on 15/11/17.
 * 所有的常量
 */
public class Constant {

    public static final String PROCESS_NAME = "com.neo.crazyphonetic";

    public static final String METHOD = "method";
    public static String SERVER_URL;

    private static final String CUR_API_VERSION_CODE = "";
    public static String FILE_ROOT;

    public static String f_image = "/image/";			    //以f_开始的都是文件系统
    public static String f_audio = "/audio/";
    public static String f_download = "/download/";
    public static String f_video = "/video/";

    public static String f_apk = "/apk/";            //下载的游戏apk,需要保存在sd卡中


    public static final int c_primary = Box.sApp.getResources().getColor(R.color.primary);
    public static final int c_111 = 0xFF111111;
    public static final int c_333 = 0xFF333333;
    public static final int c_eee = 0xFFEEEEEE;
    public static final int c_red = 0xFFFF0000;
    public static final int c_666 = 0xFF666666;
    public static final int c_777 = 0xFF777777;
    public static final int c_f6 = 0xFFF6F6F6;
    public static final int c_aaa = 0xFFAAAAAA;
    public static final int c_bg = 0xFFF9F9F9;
    public static final int c_white = 0xFFFFFFFF;
    public static final int c_transparent = 0x00000000;
    public static final int c_green = Box.sApp.getResources().getColor(R.color.green1);


    public static final String API_VERSION = "api_version";
    public static final String DATA = "data";
    public static final String API_KEY = "mianmian2015";

    public static final String VERSION_CODE = "version_code";

    public static final String LAST_ID = "last_id";
    public static final String REQUEST_NUM = "request_num";

    public static final String LOCAL_VERSION_CODE = "LOCAL_VERSION_CODE";
    public static final String LOCAL_VERSION_NAME = "LOCAL_VERSION_NAME";

    public static final int MIN_COUNT_SET_UP_LEGION = 15;

    public static final String OPTIONS = "options";

    public static final String TITLE = "title";

    public static final String TAG = "tag";

    public static final String CHARGE = "charge";
    public static final String DIARY = "diary";
    public static final String COMMENT = "comment";
    public static final String GUILD = "guild";
    public static final String CANDIDATE = "candidate";
    public static final String CANDIDATE_ID = "candidate_id";
    public static final String GUILD_ID = "guild_id";
    public static final String GUILD_DETAIL = "guild_detail";
    public static final String LEGION = "legion";
    public static final String LEGION_ID = "legion_id";
    public static final String BANK_INFO = "bank_info";

    public static final String AUTHOR = "author";
    public static final String ARTICLE = "article";
    public static final String CATEGORY = "category";

    public static final String INDEX = "index";
    public static final String USER_SYS = "user_system";
    public static final String DEVICE_ID = "user_device_id";
    public static final String SYS_VERSION_ID = "user_system_version_id";

    public static final String IMEI = "imei";


    public static final String INCOME = "income";
    public static final String POSITION = "position";

    public static final String IMAGE_MSG_LOCAL = "IMAGE_MSG_LOCAL";
    public static final String IMAGE_MSG_THUMB = "IMAGE_MSG_THUMB";
    public static final String VOICE_MSG_URI = "VOICE_MSG_URI";
    public static final String VOICE_MSG_DURATION = "VOICE_MSG_DURATION";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String USER_AVATAR = "user_avatar";
    public static final String USER = "USER";
    public static final String USER_NAME = "user_name";
    public static final String USER_LEGION_ROLE = "user_role_level";
    public static final String USER_SYS_ROLE = "user_sys_role";

    public static final String GAME = "game";

    public static final String GROUP_ID = "GROUP_ID";
    public static final String GROUP_NAME = "GROUP_NAME";
    public static final String GROUP_AVATAR = "GROUP_AVATAR";

    public static final String CITY = "city";

    public static final String CID = "conversation_id";
    public static final String C_TYPE = "conversation_type";
    public static final String C_TITLE = "conversation_title";
    public static final String C_AVATAR = "conversation_avatar";
    public static final String UID = "user_id";
    public static final String TOKEN = "token";
    public static final String AVATAR = "avatar";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String TARGET_USER_ID = "target_user_id";
    public static final String CONTENT = "content";

    public static final String LAST_LOGIN_USER_PHONE = "last_login_user_phone";

    public static final int BAD_TOKEN = -90;                //token不正确，但是数据可以返回
    public static final int INVALID_TOKEN = -99;            //错误的token


    public static final int REQUEST_CODE_EDIT_NICKNAME = 2048;
    public static final int REQUEST_CODE_EDIT_GENDER = 2049;
    public static final int REQUEST_CODE_PICKER_AVATAR = 2050;
    public static final int REQUEST_CODE_LOGIN = 2051;
    public static final int REQUEST_SELECT_FLAG = 2052;
    public static final int REQUEST_CODE_PUBLISH_NOTICE = 2053;
    public static final int REQUEST_CODE_CHOOSE_GUILD_CITY = 2054;
    public static final int REQUEST_CODE_EDIT_BANK_INFO_NAME = 2055;
    public static final int REQUEST_CODE_EDIT_BANK_INFO_CARD = 2056;
    public static final int REQUEST_CODE_EDIT_BANK_INFO_ADDRESS = 2057;
    public static final int REQUEST_CODE_TAKE_PHOTO = 2018;
    public static final int REQUEST_CODE_SELECT_PICTURE = 2019;

    public static final float BANNER_HEIGHT_SCALE = .55f;

    public static final String BUNDLE = "bundle";

    public static final String FLAG = "flag";




    public static final String m_login = "user/login";      //以m_开头的都是方法名
    public static final String m_logout = "user/logout";
    public static final String m_register = "user/register";
    public static final String m_reset = "user/reset";
    public static final String m_edit = "user/edit";
    public static final String m_code = "user/code";
    public static final String m_profile = "user/profile";
    public static final String m_my_guild= "user/myGuild";
    public static final String m_get_random_user_name = "user/random";
    public static final String m_add_friend = "user/friend";
    public static final String m_delete_friend = "user/deleteFriend";
    public static final String m_get_bank_info = "user/bankInfo";
    public static final String m_update_bank_info = "user/editBankInfo";
    public static final String m_apply_for_cash = "user/applyForCash";
    public static final String m_charge = "user/charge";
    public static final String m_get_income_list = "user/income";

    public static final String m_pingxx_charge = "pingpp/charge";


    public static final String m_create_legion = "legion/create";

    public static final String m_join_legion = "legion/join";
    public static final String m_quit_legion = "legion/quit";
    public static final String m_get_legion_list = "legion/items";
    public static final String m_get_legion_detail = "legion/detail";
    public static final String m_setup_legion = "legion/setup";
    public static final String m_get_legion_flag = "legion/flag";
    public static final String m_legion_settle_game = "legion/invade";
    public static final String m_legion_cancel_settle_game = "legion/cancelInvade";
    public static final String m_legion_invite_user = "legion/invite";
    public static final String m_public_legion_notice = "legion/publishLegionNotice";
    public static final String m_get_legion_notice_list = "legion/invite";
    public static final String m_edit_legion = "legion/editLegionInfo";
    public static final String m_legion_game_list = "legion/legionGameInfo";


    public static final String m_guild_edit = "guild/edit";
    public static final String m_quit_guild ="guild/quit";
    public static final String m_join_guild = "guild/join";
    public static final String m_guild_list = "guild/items";
    public static final String m_guild_detail = "guild/detail";
    public static final String m_send_candidate_gift = "guild/candidateSendGift";
    public static final String m_get_candidate_gift_contribution_list = "guild/candidateGiftContributionList";
    public static final String m_get_guild_city_list = "guild/guildCityList";
    public static final String m_check_in = "guild/checkIn";
    public static final String m_get_check_in_list = "guild/getUserCheckInInfo";
    public static final String m_get_candidate_mm_list = "guild/candidateList";
    public static final String m_leave_message_to_candidate = "guild/candidateComment";
    public static final String m_get_candidate_message_list = "guild/candidateCommentList";
    public static final String m_get_candidate_detail = "guild/candidateDetail";
    public static final String m_get_candidate_playing_games = "guild/candidateGame";

    public static final String m_search_guild = "search/guild";
    public static final String m_search_legion = "search/legion";
    public static final String m_search_game = "search/game";

    public static final String m_get_game_detail = "game/detail";
    public static final String m_get_discover_game_list = "game/items";
    public static final String m_get_game_list = "game/items";
    public static final String m_game_download = "game/download";
    public static final String m_game_user_game_list = "game/userGameList";
    public static final String m_game_legion_list = "game/gameLegionList";
    public static final String m_game_gift_list = "game/gameGiftList";
    public static final String m_game_server_list = "game/gameServerList";
    public static final String m_distribute_guild_gift = "game/guildPublishGameGift";
    public static final String m_distribute_legion_gift = "game/legionPublishGameGift";
    public static final String m_get_game_type_list = "game/gameTypeList";
    public static final String m_receive_game_gift = "game/userFetchGameGift";
    public static final String m_my_gift_list = "game/userGameGiftList";

    public static final String m_rank_guild = "rank/guild";
    public static final String m_rank_mm = "rank/guildCandidate";
    public static final String m_rank_legion = "rank/legion";
    public static final String m_rank_game = "rank/game";
    public static final String m_rank_user = "rank/user";

    public static final String m_publish_diary = "dynamic/publish";
    public static final String m_delete_diary= "dynamic/delete";
    public static final String m_complain_diary = "dynamic/complain";
    public static final String m_diary_list = "dynamic/items";
    public static final String m_comment_diary = "dynamic/comment";
    public static final String m_diary_detail = "dynamic/detail";

    public static final String m_complain = "tool/complain";

    public static final String m_article_publish = "article/publish";
    public static final String m_article_detail = "article/detail";
    public static final String m_article_list = "article/articleList";
    public static final String m_author_list = "article/author";
    public static final String m_interview = "article/interview";
    public static final String m_bread = "article/bread";
    public static final String m_column = "article/column";
}
