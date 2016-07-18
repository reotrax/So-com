package com.socom.so_com.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.socom.so_com.Account;
import com.socom.so_com.AsyncTimeLine;
import com.socom.so_com.R;
import com.socom.so_com.TimeLineOneAdapter;
import com.socom.so_com.TwData;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwitterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwitterFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TwitterFragment extends Fragment implements LoaderManager.LoaderCallbacks, SwipeRefreshLayout.OnRefreshListener {

    // TODO: args
    private View view;
    private Twitter twitter;
    private TimeLineOneAdapter adapter;
    private ListView listView;
    private SwipeRefreshLayout swipe;
    private int pageNum = 0;
    private int tweetGetLimist = 20;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Twitter.
     */
    // TODO: Rename and change types and number of parameters
    public static TwitterFragment newInstance(String param1, String param2) {
        TwitterFragment fragment = new TwitterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TwitterFragment() {
        // Required empty public constructor
    }


    /**
     * Android 6.0以上で呼ばれるらしい。
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachContext(context);
    }

    /**
     * 非推奨deprecatedだが、Android 6.0未満ではこちらしか呼ばれない必要らしいので用意した。
     *
     * @param activity
     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
//        onAttachContext(activity);
//    }
//}}
    /**
     * onAttach(Context)、onAttach(Activity)の両方から呼ばれるので、
     * onAttachの処理はここに書く。
     * http://qiita.com/syarihu/items/a8bb189d33585238c287
     * @param context
     */
    private void onAttachContext(Context context) {
        // Twitter設定
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerSecret(Account.ConsumerAPISecret);
        configurationBuilder.setOAuthConsumerKey(Account.ConsumerAPIKey);
        configurationBuilder.setOAuthAccessToken(Account.AccessToken);
        configurationBuilder.setOAuthAccessTokenSecret(Account.AccessTokenSecret);
        // Twitter初期化
        twitter = new TwitterFactory(configurationBuilder.build()).getInstance();

//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_twitter, container, false);

        // メイン画面
        listView = (ListView)view.findViewById(R.id.timeLine);
        swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipelayout);

        // アカウント情報：非同期で取得 ＜1,doInBackground 2,onProgressUpdate 3,onPostExecute＞
        //accountGet(twitter);

        // タイムライン取得
        startLoader(pageNum);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

	/**
     * スワイプリフレッシュ
     */
    @Override
    public void onRefresh() {
        startLoader(pageNum);
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
                swipe.setRefreshing(false);
//            }
//        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    /**
     * Loaderの初期化
     * @param i Loaderの切り替え
     */
    public void startLoader(int i) {
        Log.v("startLoader", "start loader" + i);
        Bundle args = new Bundle();
        args.putString("null", "args...");
        getLoaderManager().initLoader(0, args, this); // LoaderCallbacksを呼ぶ
    }

    /**
     * Loaderの選択と生成
     */
    @Override
    public Loader<ArrayList> onCreateLoader(int id, Bundle args) {
        Log.v("createLoader", "id = " + id);

        Loader loader = null;

        switch (id) {
            case 0:
                Log.v("case1","case 1");
                loader = new AsyncTimeLine(getContext(), twitter);
                break;

            default:
                Log.v("default","default");
                break;

        }

        return loader;
    }


    /**
     * Loaderの非同期処理後の処理
     */
    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.v("onLoadFinished", "count = " + ((ArrayList)data).size());

        /**
         * 取得結果からタイムラインを設定
         * @param timeLine タイムライン
         * @param responseList タイムライン元データ
         */
        ArrayList<TwData> timeLine = new ArrayList<>();
        ResponseList<Status> responseList = (ResponseList) data;

        try {
            for (Status status : responseList) {
                String retweetUser = " ";

                timeLine.add(new TwData(
                        status.getText(),
                        status.getCreatedAt(),
                        status.getFavoriteCount(),
                        status.getRetweetCount(),
                        status.getUser().getScreenName(),
                        retweetUser
                ));

                status.getText();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.v("tweet", responseList.get(0).toString());

        // タイムライン表示設定
        if (adapter == null) {
            adapter = new TimeLineOneAdapter(getContext(), 0);
            adapter.setTweetList(timeLine);
        } else {
//            adapter
        }
        listView.setAdapter(adapter);

        getLoaderManager().destroyLoader(pageNum);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

}
