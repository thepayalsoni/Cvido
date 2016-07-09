package com.cvido;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akslibrary.request.GetRequest;
import com.akslibrary.request.PostRequest;
import com.akslibrary.utility.Util;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvido.application.CvidoApplication;
import com.cvido.application.MyVolley;
import com.cvido.model.MessageDetail;
import com.cvido.model.SendMessage;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageDetailActivity extends Activity {
    String msgOwner, msgId;
    ListView list_alert;
    OpxCategoryAdapter adapter;
    ArrayList<MessageDetail.Datum> arrAds;
    ProgressDialog mProgressDialog;
    ImageLoader imageLoader;
    DisplayImageOptions optionsUser;
    ArrayList<MessageDetail.Datum> arrTemp;
    Button btnSendMessage;
    EditText txtMsg;

    MessageDetail myResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        msgOwner = getIntent().getExtras().getString("msgOwner");
        msgId = getIntent().getExtras().getString("msgId");


        ((TextView) this.findViewById(R.id.lblTitle)).setText("Reply");

        list_alert = (ListView) findViewById(R.id.list_alert);
        btnSendMessage = (Button) findViewById(R.id.btn_send_message);
        txtMsg = (EditText) findViewById(R.id.txt_message_description);

        getAdsData();

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtMsg != null && !txtMsg.getText().toString().isEmpty()) {
                    sendMessage(msgId, txtMsg.getText().toString().trim());
                } else
                    Toast.makeText(getApplicationContext(), "Please Enter Text", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAdsData() {


        Response.Listener<MessageDetail> onSuccess = new Response.Listener<MessageDetail>() {
            @Override
            public void onResponse(final MessageDetail response) {
                myResponse = response;
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                //Update InAdap
                arrAds = new ArrayList<MessageDetail.Datum>(response.getData());
                arrTemp = new ArrayList<>(arrAds);
                if (response.isSuccess()) {
                    if (arrAds.size() == 0) {
                        Util.showErrorBox(MessageDetailActivity.this, "Error", response.getMessage());
                    } else {

                        adapter = new OpxCategoryAdapter(MessageDetailActivity.this, response);
                        list_alert.setAdapter(adapter);
                        txtMsg.setText("");
                        Util.hideKeyBoard(MessageDetailActivity.this, txtMsg);
                        if (adapter == null) {
                            adapter = new OpxCategoryAdapter(MessageDetailActivity.this, response);
                            list_alert.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    Util.showErrorBox(MessageDetailActivity.this, "Error", "Somethings wrong! Try Again!");
                }

            }
        };

        Response.ErrorListener onError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                Util.showErrorBox(MessageDetailActivity.this, "Error", "" + error.getMessage());
            }
        };

        if (Util.hasInternetAccess(MessageDetailActivity.this)) {
            try {
                this.mProgressDialog = ProgressDialog.show(MessageDetailActivity.this, null, "Loading...", true, false);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            RequestQueue queue = MyVolley.getRequestQueue();
            Type type = new TypeToken<MessageDetail>() {
            }.getType();
            GetRequest lRequest = new GetRequest(MessageDetailActivity.this, type, getString(R.string.URL_MESSAGE_DETAIL, msgId, CvidoApplication.getAppliation().getRegister().getData().getAuthKey()), onSuccess, onError, Request.Priority.HIGH);
            queue.add(lRequest);
        } else {
            Toast.makeText(MessageDetailActivity.this, getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
        }
    }


    public class OpxCategoryAdapter extends BaseAdapter {

        protected ArrayList<MessageDetail.Datum> arrCategoriesDatas;
        private LayoutInflater mInflater;
        private Context context;
        MessageDetail msgDataa;


        public OpxCategoryAdapter(Context context, MessageDetail msgDataa) {
            this.arrCategoriesDatas = msgDataa.getData();
            this.mInflater = LayoutInflater.from(context);
            this.msgDataa = msgDataa;
            this.context = context;

            imageLoader = ImageLoader.getInstance();
            optionsUser = new DisplayImageOptions.Builder().cacheInMemory(true).showImageForEmptyUri(R.drawable.no_image)
                    .showImageOnFail(R.drawable.no_image).cacheOnDisc(true).build();
        }


        public void setMenu(ArrayList<MessageDetail.Datum> arrCategoriesDatas) {
            if (arrCategoriesDatas != null && arrCategoriesDatas.size() > 0) {
                this.arrCategoriesDatas = arrCategoriesDatas;
            }
        }


        public ArrayList<MessageDetail.Datum> getCategoriesDatas() {
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
        public MessageDetail.Datum getItem(int position) {
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
                convertView = mInflater.inflate(R.layout.item_message_details, parent, false);
                holder = new ViewHolder(convertView);
            }

            if (getItem(position).isRead == 1)
                holder.rlMessageParent.setBackgroundResource(android.R.color.darker_gray);
            else
                holder.rlMessageParent.setBackgroundResource(R.color.white);


            holder.lblMessageUser.setText(myResponse.getSenderName());
            imageLoader.displayImage(myResponse.getSenderAvatar(), holder.imgMessageUser, optionsUser);


            holder.lblMessageDecription.setText(getItem(position).getMessage());
            holder.lblMessageDate.setText(Util.getDateFromCurrent(getItem(position).getCreatedAt(), getString(R.string.date_formate_1)));


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


    private void sendMessage(String userId, String message) {

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("CvMessageThreads[cv_message_id]", userId);
        hashMap.put("CvMessageThreads[message]", message);

        Response.Listener<SendMessage> onSuccess = new Response.Listener<SendMessage>() {
            @Override
            public void onResponse(final SendMessage response) {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if (response.isSuccess()) {
                    getAdsData();
                    Toast.makeText(MessageDetailActivity.this, "" + response.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Util.showErrorBox(MessageDetailActivity.this, "Error", response.getMessage());
                }

            }
        };

        Response.ErrorListener onError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

            }
        };

        if (Util.hasInternetAccess(MessageDetailActivity.this)) {
            //  showProgressDialog(null, getString(R.string.str_FetchingData), false);
            try {
                this.mProgressDialog = ProgressDialog.show(MessageDetailActivity.this, null, "Loading...", true, false);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            RequestQueue queue = MyVolley.getRequestQueue();
            Type type = new TypeToken<SendMessage>() {
            }.getType();
            PostRequest lRequest = new PostRequest(MessageDetailActivity.this, type, getString(R.string.URL_REPLY_MESSAGE, CvidoApplication.getAppliation().getRegister().getData().getAuthKey()), hashMap, onSuccess, onError, Request.Priority.HIGH);
            queue.add(lRequest);
        } else {
            Toast.makeText(MessageDetailActivity.this, getString(R.string.error_no_internet), Toast.LENGTH_LONG).show();
        }
    }


}
