package com.apphelp.help.thetatechnolabs.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apphelp.help.thetatechnolabs.R;
import com.apphelp.help.thetatechnolabs.controller.Global;
import com.apphelp.help.thetatechnolabs.model.Bean;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home_Fragment extends Fragment {

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDoalog;

    RecyclerView rc_userdata;
    Bean.UserData userData_model;
    ArrayList<Bean.UserData> model_modelArrayList = new ArrayList<>();
    UserData_Adapater userData_adapater;

    SwipeRefreshLayout swipeToRefresh;

    public Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestQueue = Volley.newRequestQueue(getActivity());

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Please wait...");
        progressDoalog.setCancelable(false);

        rc_userdata =  rootView.findViewById(R.id.rc_userdata);
        rc_userdata.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rc_userdata.setLayoutManager(linearLayoutManager);

        swipeToRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        swipeToRefresh.setColorSchemeResources(R.color.colorApp);

        try {

            getUserData();

        } catch (Exception e) {

            e.printStackTrace();
        }


        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    getUserData();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private void getUserData() {
        progressDoalog.show();
        swipeToRefresh.setRefreshing(false);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Global.BASE_URL + "users?page=1", new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                progressDoalog.dismiss();
                Log.i("CHANGE_PAS_RESPONSE", response.toString());
                // TODO Auto-generated method stub
                try {

                    String page = response.getString("page");
                    String per_page = response.getString("per_page");
                    String total = response.getString("total");
                    String total_pages = response.getString("total_pages");

                    Log.d("page", page);
                    Log.d("per_page", per_page);
                    Log.d("total_pages", total_pages);
                    Log.d("total", total);

                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.i("ShowCommentList_Array", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);

                        userData_model = new Bean.UserData();

                        userData_model.setId(obj.getString("id"));
                        userData_model.setEmail(obj.getString("email"));
                        userData_model.setFirst_name(obj.getString("first_name"));
                        userData_model.setLast_name(obj.getString("last_name"));
                        userData_model.setImage(obj.getString("avatar"));

                        model_modelArrayList.add(userData_model);
                        Log.e("ListBatav", model_modelArrayList.toString());
                    }

                    if (model_modelArrayList.size() > 0) {

                        userData_adapater = new UserData_Adapater(getContext(), model_modelArrayList);
                        rc_userdata.setAdapter(userData_adapater);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                progressDoalog.dismiss();
//                swipeToRefresh.setRefreshing(false);
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == 404) {
                    Toast.makeText(getContext(), "No Record Found.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getContext(), "Time Out,Network is slow.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getContext(), "Authentication Error.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getContext(), "Server Not Connected.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getContext(), "Parse Error.", Toast.LENGTH_SHORT).show();
                }

                Log.i("error --> ", error.toString());

            }

        });
        requestQueue.getCache().clear();
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjReq);
    }

    private class UserData_Adapater extends RecyclerView.Adapter<UserData_Adapater.MyHolder> {

        private List<Bean.UserData> n_modelList = null;
        private Context mContext;

        public UserData_Adapater(Context context, ArrayList<Bean.UserData> model_modelArrayList) {

            this.mContext = context;
            this.n_modelList = model_modelArrayList;
        }

        @NonNull
        @Override
        public UserData_Adapater.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_data_activity, parent, false);
            Home_Fragment.UserData_Adapater.MyHolder viewHolder = new Home_Fragment.UserData_Adapater.MyHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserData_Adapater.MyHolder holder, int position) {
            Bean.UserData ReportModel = n_modelList.get(position);
            if (ReportModel.getFirst_name().equalsIgnoreCase("") || ReportModel.getFirst_name().equalsIgnoreCase("null") || ReportModel.getFirst_name().equalsIgnoreCase(null)) {

                holder.txt_first_name.setText("");

            } else {

                holder.txt_first_name.setText(ReportModel.getFirst_name());
            }

            if (ReportModel.getLast_name().equalsIgnoreCase("") || ReportModel.getLast_name().equalsIgnoreCase("null") || ReportModel.getLast_name().equalsIgnoreCase(null)) {

                holder.txt_last_name.setText("");

            } else {

                holder.txt_last_name.setText(ReportModel.getLast_name());
            }

            if (ReportModel.getEmail().equalsIgnoreCase("") || ReportModel.getEmail().equalsIgnoreCase("null") || ReportModel.getEmail().equalsIgnoreCase(null)) {

                holder.txt_email.setText("");

            } else {

                holder.txt_email.setText(ReportModel.getEmail());
            }

            if (ReportModel.getImage().isEmpty()){

                Picasso.with(mContext)
                        .load(R.drawable.theta_logo)
                        .noFade()
                        .into(holder.img_user);

            } else {

                Picasso.with(mContext)
                        .load(ReportModel.getImage())
                        .noFade()
                        .into(holder.img_user);
            }
        }

        @Override
        public int getItemCount() {
            return n_modelList.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView img_user;
            TextView txt_first_name, txt_last_name, txt_email;
            public MyHolder(@NonNull View itemView) {
                super(itemView);

                img_user = (ImageView) itemView.findViewById(R.id.img_user);

                txt_first_name = (TextView) itemView.findViewById(R.id.txt_first_name);
                txt_last_name = (TextView) itemView.findViewById(R.id.txt_last_name);
                txt_email = (TextView) itemView.findViewById(R.id.txt_email);
            }
        }
    }
}
