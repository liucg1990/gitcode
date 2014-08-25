package player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.liucg.demo.R;
import com.liucg.demo.common.Utility;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnTimedTextListener;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends Activity implements OnCompletionListener,OnPreparedListener{
	private MediaPlayer mp = new MediaPlayer();
	private TextView tipTextView;
	private ArrayList<String> songArrayList;
	private int songIndex;
	private Timer songTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		tipTextView  = (TextView) findViewById(R.id.textview_player_tip);
		mp.setOnPreparedListener(this);
		mp.setOnCompletionListener(this);
		songTimer = new Timer();
		songArrayList = new Utility().scanFiles(Environment.getExternalStorageDirectory().getAbsolutePath()+"/qqmusic/song");
		Log.i("files",songArrayList.toString());
		playMusic();
		songTimer.schedule(songTask, 0, 1000);
		//develop
		//test
		//test 中文
		//test 1551 1
		//test 1551 2
		//test develop 1
		//test develop 2
		//test 1551 3
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		upDateTip(mp);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		upDateTip(mp);
		if(mp != null){
			if(songIndex < songArrayList.size()){
				songIndex ++;
				mp.reset();
				playMusic();	
			}
		}
	}
	
	private void playMusic(){
		try {
			mp.setDataSource(songArrayList.get(songIndex));
			mp.prepare();
			mp.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void upDateTip(MediaPlayer mp){
		if(tipTextView != null && mp != null){
			tipTextView.setText(mp.getCurrentPosition()+"/"+mp.getDuration());
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
//		if(mp.isPlaying()){
//			mp.pause();
//		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.stop();
		mp.release();
		mp = null;
	}

	
	private Handler songHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 0){
				int duration = mp.getDuration();
				int currentPostion = mp.getCurrentPosition();
				tipTextView.setText(currentPostion + "/" + duration);
			}
		}
		
	};
	
	private TimerTask songTask = new TimerTask() {
		
		@Override
		public void run() {
			if(mp.isPlaying()){
				songHandler.sendEmptyMessage(0);
			}
		}
	};

	

}
