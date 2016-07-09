package com.cvido.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akslibrary.request.GetRequest;
import com.akslibrary.utility.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvido.HomeActivity;
import com.cvido.MessageDetailActivity;
import com.cvido.R;
import com.cvido.application.CvidoApplication;
import com.cvido.application.MyVolley;
import com.cvido.model.MessageData;
import com.cvido.model.MessageData.Data;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MessageFragment extends Fragment {
    ListView list_alert;
    OpxCategoryAdapter adapter;
    ArrayList<Data.Cv> arrAds;
    ProgressDialog mProgressDialog;
    ImageLoader imageLoader;
    DisplayImageOptions optionsUser;
    MessageData myResponse;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        Toolbar toolbar = ((HomeActivity) getActivity()).toolbar;
        TextView title_toolbar = (TextView) toolbar.findViewById(R.id.title_toolbar);
        title_toolbar.setText(getString(R.string.str_message_fragment));


        arrAds = new ArrayList<Data.Cv>();
        list_alert = (ListView) rootView.findViewById(R.id.list_alert);
        list_alert.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent inte = new Intent(getActivity(), MessageDetailActivity.class);
                inte.putExtra("msgId", "" + adapter.getItem(position).getCvMessageId());
                inte.putExtra("msgOwner", "" + myResponse.getSenderName());
                startActivity(inte);

            }
        });

        getAdsData();


        return rootView;
    }

    public class OpxCategoryAdapter extends BaseAdapter {

        protected ArrayList<Data.Cv> arrCategoriesDatas;
        private LayoutInflater mInflater;
        private Context context;

        public OpxCategoryAdapter(Context context, ArrayList<Data.Cv> arrCategoriesDatas) {
            this.arrCategoriesDatas = arrCategoriesDatas;
            this.mInflater = LayoutInflater.from(context);
            this.context = context;

            imageLoader = ImageLoader.getInstance();
            optionsUser = new DisplayImageOptions.Builder().cacheInMemory(true).showImageForEmptyUri(R.drawable.avtar_icon)
                    .showImageOnFail(R.drawable.avtar_icon).cacheOnDisc(true).build();
        }


        public void setMenu(ArrayList<Data.Cv> arrCategoriesDatas) {
            if (arrCategoriesDatas != null && arrCategoriesDatas.size() > 0) {
                this.arrCategoriesDatas = arrCategoriesDatas;
            }
        }


        public ArrayList<Data.Cv> getCategoriesDatas() {
            return arrCategoriesDatas;
        }

        @Override
        public int getCount() {
            if (arrCategoriesDatas != null) {
                return arrCategoriesDatas.size();
            }
            return 0;
        }

        @Override
        public Data.Cv getItem(int position) {
            return arrCategoriesDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.item_message, parent, false);
                holder = new ViewHolder(convertView);
            }

            if (getItem(position).getIsRead() == 1)
                holder.rlMessageParent.setBackgroundResource(android.R.color.black);
            else
                holder.rlMessageParent.setBackgroundResource(R.color.color_message_unread);

            holder.lblMessageUser.setText("" + myResponse.getSenderName());
            holder.lblMessageDecription.setText(getItem(position).getMessage());
            holder.lblMessageDate.setText(Util.getDateFromCurrent(getItem(position).getCreatedAt(), getString(R.string.date_formate_1)));

            imageLoader.displayImage(myResponse.getSenderAvatar(), holder.imgMessageUser, optionsUser);

            return convertView;
        }

        protected class ViewHolder {

            TextView lblMessageUser, lblMessageDecription, lblMessageDate;
            ImageView imgMessageUser;
            RelativeLayout rlMessageParent;

            public ViewHolder(View view) {
                lblMessageUser = ((TextView) view.findViewById(R.id.lblMessageUser));
                lblMessageDecription = ((TextView) view.findViewById(R.id.lblMessageDecription));
                lblMessageDate = ((TextView) view.findViewById(R.id.lblMessageDate));
                imgMessageUser = ((ImageView) view.findViewById(R.id.imgMessageUser));
                rlMessageParent = (RelativeLayout) view.findViewById(R.id.rlMessageParent);

                view.setTag(this);
            }
        }


    }
    ///////////////////////////////////////////////////

    private void getAdsData() {


        Response.Listener<MessageData> onSuccess = new Response.Listener<MessageData>() {
            @Override
            public void onResponse(final MessageData response) {

                myResponse = response;
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                //Update InAdap

                arrAds = new ArrayList<Data.Cv>(response.getData().getCvs());

                if (response.isSuccess()) {
                    if (arrAds.size() == 0) {
                        Util.showErrorBox(getActivity(), "Error", response.getMessage());
                    } else {
                        if (adapter == null)
                            adapter = new OpxCategoryAdapter(getActivity(), arrAds);

                        list_alert.setAdapter(adapter);
                    }
                } else {
                    Util.showErrorBox(getActivity(), "Error", "Somethings wrong! Try Again!");
                }

            }
        };

        Response.ErrorListener onError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Util.showErrorBox(getActivity(), "Error", "" + error.getMessage());
            }
        };

        if (Util.hasInternetAccess(getActivity())) {
            try {
                this.mProgressDialog = ProgressDialog.show(getActivity(), null, "Loading...", true, false);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            RequestQueue queue = MyVolley.getRequestQueue();
            Type type = new TypeToken<MessageData>() {
            }.getType();

            GetRequest lRequest = new GetRequest(getActivity(), type, getString(R.string.URL_MESSAGE, CvidoApplication.getAppliation().getRegister().getData().getAuthKey()), onSuccess, onError, Request.Priority.HIGH);
            queue.add(lRequest);
        } else {
            Toast.makeText(getActivity(), getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
        }
    }
}
