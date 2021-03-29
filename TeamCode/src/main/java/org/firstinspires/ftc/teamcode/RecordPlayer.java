package org.firstinspires.ftc.teamcode;

import android.content.Context;
import com.qualcomm.ftccommon.SoundPlayer;

public class RecordPlayer {

  // setup class initalization
  public RecordPlayer(Context context) {
    this.myApp = context;
  }

  private Context myApp;
  private static boolean playing = false;
  private static int soundID = -1;

  String sounds[] = {
    "ss_alarm",
    "ss_bb8_down",
    "ss_bb8_up",
    "ss_darth_vader",
    "ss_fly_by",
    "ss_mf_fail",
    "ss_laser",
    "ss_laser_burst",
    "ss_light_saber",
    "ss_light_saber_long",
    "ss_light_saber_short",
    "ss_light_speed",
    "ss_mine",
    "ss_power_up",
    "ss_r2d2_up",
    "ss_roger_roger",
    "ss_siren",
    "ss_wookie",
  };

  public String[] getSounds() {
    return sounds;
  }

  public void playSound(String soundName) {
    // Context myApp = hardwareMap.appContext;
    SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
    params.loopControl = 0;
    params.waitForNonLoopingSoundsToFinish = true;

    if (!playing) {
      playing = true;
      if ((soundID = myApp.getResources().getIdentifier(soundName, "raw", myApp.getPackageName()))
          != 0) {
        com.qualcomm.ftccommon.SoundPlayer.getInstance()
            .startPlaying(
                myApp,
                soundID,
                params,
                null,
                new Runnable() {
                  public void run() {
                    playing = false;
                  }
                });
      }
    }
  }
}
