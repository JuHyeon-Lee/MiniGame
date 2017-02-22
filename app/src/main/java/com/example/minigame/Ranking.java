package com.example.minigame;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends TabActivity {

    ArrayList<Scores_Card> datas_card = new ArrayList<Scores_Card>();
    ArrayList<Scores_Dudogi> datas_dudogi = new ArrayList<Scores_Dudogi>();
    ArrayList<Scores_Muffin> datas_muffin = new ArrayList<Scores_Muffin>();
    ArrayList<Scores_Waffle> datas_waffle = new ArrayList<Scores_Waffle>();

    String player_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.layout_ranking, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("두더지")
                .setContent(R.id.tab_dudogi));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("머핀")
                .setContent(R.id.tab_muffin));

        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("카드")
                .setContent(R.id.tab_card));

        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator("바위깨기")
                .setContent(R.id.tab_waffle));

        tabHost.getTabWidget().getChildAt(0)
                .setBackgroundColor(Color.parseColor("#9925b6d2"));
        tabHost.getTabWidget().getChildAt(1)
                .setBackgroundColor(Color.parseColor("#9925b6d2"));
        tabHost.getTabWidget().getChildAt(2)
                .setBackgroundColor(Color.parseColor("#9925b6d2"));
        tabHost.getTabWidget().getChildAt(3)
                .setBackgroundColor(Color.parseColor("#9925b6d2"));

        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );


        final Ranking.DudogiAdapter DudogiAdapter = new Ranking.DudogiAdapter(inflater, datas_dudogi);

        ListView listView_dudogi = (ListView)findViewById(R.id.listView_dudogi);
        listView_dudogi.setAdapter(DudogiAdapter);


        final Ranking.WaffleAdapter WaffleAdapter = new Ranking.WaffleAdapter(inflater, datas_waffle);

        ListView listView_waffle = (ListView)findViewById(R.id.listView_waffle);
        listView_waffle.setAdapter(WaffleAdapter);


        final Ranking.CardAdapter CardAdapter = new Ranking.CardAdapter(inflater, datas_card);

        ListView listView_card = (ListView)findViewById(R.id.listView_card);
        listView_card.setAdapter(CardAdapter);


        final Ranking.MuffinAdapter MuffinAdapter = new Ranking.MuffinAdapter(inflater, datas_muffin);

        ListView listView_muffin = (ListView)findViewById(R.id.listView_muffin);
        listView_muffin.setAdapter(MuffinAdapter);

        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name_search = (EditText) findViewById(R.id.name_search);
                String name = name_search.getText().toString();

                ShowProfile(name);
            }
        });

    }

    public void ShowProfile(String name){

        for(int i=0; i<datas_dudogi.size(); i++){
            Scores_Dudogi ft=datas_dudogi.get(i);
            if(name.equals(ft.getName())){

                Dialog dialog1 = new Dialog(this);
                dialog1.setContentView(R.layout.layout_profile);
                dialog1.setTitle("프로필 정보");

                TextView name_profile = (TextView) dialog1.findViewById(R.id.name_profile);
                name_profile.setText(ft.getName());

                ImageView iv = (ImageView) dialog1.findViewById(R.id.image_profile);
                iv.setImageResource(ft.getD());

                TableLayout tb = (TableLayout) dialog1.findViewById(R.id.info_profile);

                TextView ranking_dudogi_profile = (TextView) dialog1.findViewById(R.id.ranking_dudogi_profile);
                TextView ranking_muffin_profile = (TextView) dialog1.findViewById(R.id.ranking_muffin_profile);
                TextView ranking_card_profile = (TextView) dialog1.findViewById(R.id.ranking_card_profile);
                TextView ranking_waffle_profile = (TextView) dialog1.findViewById(R.id.ranking_waffle_profile);

                TextView score_dudogi_profile = (TextView) dialog1.findViewById(R.id.score_dudogi_profile);
                TextView score_muffin_profile = (TextView) dialog1.findViewById(R.id.score_muffin_profile);
                TextView score_card_profile = (TextView) dialog1.findViewById(R.id.score_card_profile);
                TextView score_waffle_profile = (TextView) dialog1.findViewById(R.id.score_waffle_profile);

                ranking_dudogi_profile.setText(Integer.toString(ft.getRank()));
                score_dudogi_profile.setText(Integer.toString(ft.getScore()));

                for(int i2=0; i<datas_muffin.size(); i2++) {
                    Scores_Muffin ft2 = datas_muffin.get(i2);
                    if (name.equals(ft2.getName())) {
                        ranking_muffin_profile.setText(Integer.toString(ft2.getRank()));
                        score_muffin_profile.setText(Integer.toString(ft2.getScore()));
                        break;
                    }
                }

                for(int i3=0; i<datas_card.size(); i3++) {
                    Scores_Card ft3 = datas_card.get(i3);
                    if (name.equals(ft3.getName())) {
                        ranking_card_profile.setText(Integer.toString(ft3.getRank()));
                        score_card_profile.setText(Integer.toString(ft3.getScore()));
                        break;
                    }
                }

                for(int i4=0; i<datas_waffle.size(); i4++) {
                    Scores_Waffle ft4 = datas_waffle.get(i4);
                    if (name.equals(ft4.getName())) {
                        ranking_waffle_profile.setText(Integer.toString(ft4.getRank()));
                        score_waffle_profile.setText(Integer.toString(ft4.getScore()));
                        break;
                    }
                }

                dialog1.show();

                break;
            }
            else if(i==(datas_dudogi.size()-1)){

                Toast toast = Toast.makeText(getApplicationContext(),"검색결과가 없습니다.", Toast.LENGTH_SHORT);
                toast.show();

                break;
            }
        }

    }

    // 점수 내림차순
    static class NoDescCompare1 implements Comparator<Scores_Dudogi> {

        @Override
        public int compare(Scores_Dudogi arg0, Scores_Dudogi arg1) {
            // TODO Auto-generated method stub
            return arg0.getScore() > arg1.getScore() ? -1 : arg0.getScore() < arg1.getScore() ? 1:0;
        }

    }

    // 점수 내림차순
    static class NoDescCompare2 implements Comparator<Scores_Waffle> {

        @Override
        public int compare(Scores_Waffle arg0, Scores_Waffle arg1) {
            // TODO Auto-generated method stub
            return arg0.getScore() > arg1.getScore() ? -1 : arg0.getScore() < arg1.getScore() ? 1:0;
        }

    }

    // 점수 내림차순
    static class NoDescCompare3 implements Comparator<Scores_Card> {

        @Override
        public int compare(Scores_Card arg0, Scores_Card arg1) {
            // TODO Auto-generated method stub
            return arg0.getScore() > arg1.getScore() ? -1 : arg0.getScore() < arg1.getScore() ? 1:0;
        }

    }

    // 점수 내림차순
    static class NoDescCompare4 implements Comparator<Scores_Muffin> {

        @Override
        public int compare(Scores_Muffin arg0, Scores_Muffin arg1) {
            // TODO Auto-generated method stub
            return arg0.getScore() > arg1.getScore() ? -1 : arg0.getScore() < arg1.getScore() ? 1:0;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        BackgroundMusic.mp.start();

        SharedPreferences rank1 = getSharedPreferences("RankingDudogi", MODE_PRIVATE);
        SharedPreferences rank2 = getSharedPreferences("RankingMuffin", MODE_PRIVATE);
        SharedPreferences rank3 = getSharedPreferences("RankingCard", MODE_PRIVATE);
        SharedPreferences rank4 = getSharedPreferences("RankingWaffle", MODE_PRIVATE);

        SharedPreferences.Editor editor1 = rank1.edit();
        SharedPreferences.Editor editor2 = rank2.edit();
        SharedPreferences.Editor editor3 = rank3.edit();
        SharedPreferences.Editor editor4 = rank4.edit();

        int end = 0;
        while(true){
            String s = rank1.getString(end+"이름", "끝");
            if(s.equals("끝")){
                break;
            }
            else
                end++;
        }

        if(end==0){
            datas_dudogi.add(new Scores_Dudogi(0, "000", 0, R.drawable.basicprofile));
            datas_muffin.add(new Scores_Muffin(0, "000", 0, R.drawable.basicprofile));
            datas_card.add(new Scores_Card(0, "000", 0, R.drawable.basicprofile));
            datas_waffle.add(new Scores_Waffle(0, "000", 0, R.drawable.basicprofile));
        }
        for(int i=0; i<end ; i++){

            datas_dudogi.add(new Scores_Dudogi(0, rank1.getString(i + "이름", "USER1"), rank1.getInt(i + "점수", 0), rank1.getInt(i + "사진", R.drawable.basicprofile)));
            datas_muffin.add(new Scores_Muffin(0, rank2.getString(i + "이름", "USER1"), rank2.getInt(i + "점수", 0), rank2.getInt(i + "사진", R.drawable.basicprofile)));
            datas_card.add(new Scores_Card(0, rank3.getString(i + "이름", "USER1"), rank3.getInt(i + "점수", 0), rank3.getInt(i + "사진", R.drawable.basicprofile)));
            datas_waffle.add(new Scores_Waffle(0, rank4.getString(i + "이름", "USER1"), rank4.getInt(i + "점수", 0), rank4.getInt(i + "사진", R.drawable.basicprofile)));

        }

        Intent intent1 = getIntent();

        int score_dudogi = intent1.getIntExtra("두더지점수",0);
        int score_waffle = intent1.getIntExtra("와플점수", 0);
        int score_card = intent1.getIntExtra("카드점수", 0);
        int score_muffin = intent1.getIntExtra("머핀점수", 0);

        player_name = intent1.getStringExtra("이름");

        for(int i=0; i<datas_dudogi.size(); i++){
            Scores_Dudogi ft=datas_dudogi.get(i);
            if(ft.getName().equals(player_name)){

                datas_dudogi.remove(i);
                datas_dudogi.add(new Scores_Dudogi(0, player_name, score_dudogi, R.drawable.basicprofile));
                Collections.sort(datas_dudogi, new Ranking.NoDescCompare1());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.DudogiAdapter DudogiAdapter = new Ranking.DudogiAdapter(inflater, datas_dudogi);
                DudogiAdapter.notifyDataSetChanged();

                break;
            }
            else if(i==(datas_dudogi.size()-1)){

                datas_dudogi.add(new Scores_Dudogi(0, player_name, score_dudogi, R.drawable.basicprofile));
                Collections.sort(datas_dudogi, new Ranking.NoDescCompare1());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.DudogiAdapter DudogiAdapter = new Ranking.DudogiAdapter(inflater, datas_dudogi);
                DudogiAdapter.notifyDataSetChanged();

                break;
            }
        }

        for(int i=0; i<datas_waffle.size(); i++){
            Scores_Waffle ft=datas_waffle.get(i);
            if(ft.getName().equals(player_name)){

                datas_waffle.remove(i);
                datas_waffle.add(new Scores_Waffle(0, player_name, score_waffle, R.drawable.basicprofile));
                Collections.sort(datas_waffle, new Ranking.NoDescCompare2());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.WaffleAdapter WaffleAdapter = new Ranking.WaffleAdapter(inflater, datas_waffle);
                WaffleAdapter.notifyDataSetChanged();

                break;
            }
            else if(i==(datas_waffle.size()-1)){

                datas_waffle.add(new Scores_Waffle(0, player_name, score_waffle, R.drawable.basicprofile));
                Collections.sort(datas_waffle, new Ranking.NoDescCompare2());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.WaffleAdapter WaffleAdapter = new Ranking.WaffleAdapter(inflater, datas_waffle);
                WaffleAdapter.notifyDataSetChanged();

                break;
            }
        }

        for(int i=0; i<datas_card.size(); i++){
            Scores_Card ft=datas_card.get(i);
            if(ft.getName().equals(player_name)){

                datas_card.remove(i);
                datas_card.add(new Scores_Card(0, player_name, score_card, R.drawable.basicprofile));
                Collections.sort(datas_card, new Ranking.NoDescCompare3());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.CardAdapter CardAdapter = new Ranking.CardAdapter(inflater, datas_card);
                CardAdapter.notifyDataSetChanged();

                break;
            }
            else if(i==(datas_card.size()-1)){

                datas_card.add(new Scores_Card(0, player_name, score_card, R.drawable.basicprofile));
                Collections.sort(datas_card, new Ranking.NoDescCompare3());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.CardAdapter CardAdapter = new Ranking.CardAdapter(inflater, datas_card);
                CardAdapter.notifyDataSetChanged();

                break;
            }
        }

        for(int i=0; i<datas_muffin.size(); i++){
            Scores_Muffin ft=datas_muffin.get(i);
            if(ft.getName().equals(player_name)){

                datas_muffin.remove(i);
                datas_muffin.add(new Scores_Muffin(0, player_name, score_muffin, R.drawable.basicprofile));
                Collections.sort(datas_muffin, new Ranking.NoDescCompare4());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.MuffinAdapter MuffinAdapter = new Ranking.MuffinAdapter(inflater, datas_muffin);
                MuffinAdapter.notifyDataSetChanged();

                break;
            }
            else if(i==(datas_muffin.size()-1)){

                datas_muffin.add(new Scores_Muffin(0, player_name, score_muffin, R.drawable.basicprofile));
                Collections.sort(datas_muffin, new Ranking.NoDescCompare4());

                LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                Ranking.MuffinAdapter MuffinAdapter = new Ranking.MuffinAdapter(inflater, datas_muffin);
                MuffinAdapter.notifyDataSetChanged();

                break;
            }
        }

        for(int i=0; i<datas_dudogi.size(); i++) {
            Scores_Dudogi ft = datas_dudogi.get(i);
            ft.setRank(i+1);
        }
        for(int i=0; i<datas_muffin.size(); i++) {
            Scores_Muffin ft = datas_muffin.get(i);
            ft.setRank(i+1);
        }
        for(int i=0; i<datas_card.size(); i++) {
            Scores_Card ft = datas_card.get(i);
            ft.setRank(i+1);
        }
        for(int i=0; i<datas_waffle.size(); i++) {
            Scores_Waffle ft = datas_waffle.get(i);
            ft.setRank(i+1);
        }

        for(int i=0; i<datas_dudogi.size(); i++) {

            Scores_Dudogi ft_dudogi=datas_dudogi.get(i);
            Scores_Muffin ft_muffin=datas_muffin.get(i);
            Scores_Card ft_card=datas_card.get(i);
            Scores_Waffle ft_waffle=datas_waffle.get(i);

            editor1.putString(i+"이름", ft_dudogi.getName());
            editor1.putInt(i+"사진", ft_dudogi.getD());
            editor1.putInt(i+"점수", ft_dudogi.getScore());

            editor2.putString(i+"이름", ft_muffin.getName());
            editor2.putInt(i+"사진", ft_muffin.getD());
            editor2.putInt(i+"점수", ft_muffin.getScore());

            editor3.putString(i+"이름", ft_card.getName());
            editor3.putInt(i+"사진", ft_card.getD());
            editor3.putInt(i+"점수", ft_card.getScore());

            editor4.putString(i+"이름", ft_waffle.getName());
            editor4.putInt(i+"사진", ft_waffle.getD());
            editor4.putInt(i+"점수", ft_waffle.getScore());

        }

        editor1.commit();
        editor2.commit();
        editor3.commit();
        editor4.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BackgroundMusic.mp.pause();
    }

    @Override
    protected void onStop() {
        datas_dudogi.clear();
        datas_muffin.clear();
        datas_card.clear();
        datas_waffle.clear();
        super.onStop();
    }

    public class DudogiAdapter extends BaseAdapter {

        ArrayList<Scores_Dudogi> datas;
        LayoutInflater inflater;

        public DudogiAdapter(LayoutInflater inflater, ArrayList<Scores_Dudogi> datas) {
            // TODO Auto-generated constructor stub
            this.datas = datas;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if( convertView==null){
                convertView= inflater.inflate(R.layout.row, null);
            }

            TextView text_name= (TextView)convertView.findViewById(R.id.name);
            TextView text_score= (TextView)convertView.findViewById(R.id.score);
            TextView text_rank= (TextView)convertView.findViewById(R.id.rank);

            text_name.setText( datas.get(position).getName() );
            text_score.setText( Integer.toString(datas.get(position).getScore()) );
            text_rank.setText( Integer.toString(datas.get(position).getRank()) );

            ImageView profile = (ImageView) convertView.findViewById(R.id.profile);
            profile.setImageResource(datas.get(position).getD());

            LinearLayout row = (LinearLayout) convertView.findViewById(R.id.layout_row);
            if(datas.get(position).getName()==player_name){
                row.setBackgroundColor(Color.parseColor("#9925b6d2"));
            }
            else
                row.setBackgroundColor(Color.parseColor("#00ff0000"));

            return convertView;
        }

    }

    public class CardAdapter extends BaseAdapter {

        ArrayList<Scores_Card> datas;
        LayoutInflater inflater;

        public CardAdapter(LayoutInflater inflater, ArrayList<Scores_Card> datas) {
            // TODO Auto-generated constructor stub
            this.datas = datas;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if( convertView==null){
                convertView= inflater.inflate(R.layout.row, null);
            }

            TextView text_name= (TextView)convertView.findViewById(R.id.name);
            TextView text_score= (TextView)convertView.findViewById(R.id.score);
            TextView text_rank= (TextView)convertView.findViewById(R.id.rank);

            text_name.setText( datas.get(position).getName() );
            text_score.setText( Integer.toString(datas.get(position).getScore()) );
            text_rank.setText( Integer.toString(datas.get(position).getRank()) );

            ImageView profile = (ImageView) convertView.findViewById(R.id.profile);
            profile.setImageResource(datas.get(position).getD());

            LinearLayout row = (LinearLayout) convertView.findViewById(R.id.layout_row);
            if(datas.get(position).getName()==player_name){
                row.setBackgroundColor(Color.parseColor("#9925b6d2"));
            }
            else
                row.setBackgroundColor(Color.parseColor("#00ff0000"));

            return convertView;
        }

    }

    public class WaffleAdapter extends BaseAdapter {

        ArrayList<Scores_Waffle> datas;
        LayoutInflater inflater;

        public WaffleAdapter(LayoutInflater inflater, ArrayList<Scores_Waffle> datas) {
            // TODO Auto-generated constructor stub
            this.datas = datas;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if( convertView==null){
                convertView= inflater.inflate(R.layout.row, null);
            }


            TextView text_name= (TextView)convertView.findViewById(R.id.name);
            TextView text_score= (TextView)convertView.findViewById(R.id.score);
            TextView text_rank= (TextView)convertView.findViewById(R.id.rank);

            text_name.setText( datas.get(position).getName() );
            text_score.setText( Integer.toString(datas.get(position).getScore()) );
            text_rank.setText( Integer.toString(datas.get(position).getRank()) );

            ImageView profile = (ImageView) convertView.findViewById(R.id.profile);
            profile.setImageResource(datas.get(position).getD());

            LinearLayout row = (LinearLayout) convertView.findViewById(R.id.layout_row);
            if(datas.get(position).getName()==player_name){
                row.setBackgroundColor(Color.parseColor("#9925b6d2"));
            }
            else
                row.setBackgroundColor(Color.parseColor("#00ff0000"));

            return convertView;
        }

    }

    public class MuffinAdapter extends BaseAdapter {

        ArrayList<Scores_Muffin> datas;
        LayoutInflater inflater;

        public MuffinAdapter(LayoutInflater inflater, ArrayList<Scores_Muffin> datas) {
            // TODO Auto-generated constructor stub
            this.datas = datas;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if( convertView==null){
                convertView= inflater.inflate(R.layout.row, null);
            }


            TextView text_name= (TextView)convertView.findViewById(R.id.name);
            TextView text_score= (TextView)convertView.findViewById(R.id.score);
            TextView text_rank= (TextView)convertView.findViewById(R.id.rank);

            text_name.setText( datas.get(position).getName() );
            text_score.setText( Integer.toString(datas.get(position).getScore()) );
            text_rank.setText( Integer.toString(datas.get(position).getRank()) );

            ImageView profile = (ImageView) convertView.findViewById(R.id.profile);
            profile.setImageResource(datas.get(position).getD());


            LinearLayout row = (LinearLayout) convertView.findViewById(R.id.layout_row);
            if(datas.get(position).getName()==player_name){
                row.setBackgroundColor(Color.parseColor("#9925b6d2"));
            }
            else
                row.setBackgroundColor(Color.parseColor("#00ff0000"));

            return convertView;
        }

    }
}
