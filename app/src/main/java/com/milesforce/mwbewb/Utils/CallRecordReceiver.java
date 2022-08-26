package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.provider.CallLog;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.milesforce.mwbewb.Utils.ConstantUtills.FILE_SAVE_TIME;

public class CallRecordReceiver extends CallStatues {
    private static final String TAG = CallRecordReceiver.class.getSimpleName();
    public static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    public static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";
    public static final String EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";

    protected CallRecord callRecord;
    private static MediaRecorder recorder;
    public static AudioManager audioManager;
    private File audiofile;
    private boolean isRecordStarted = false;
    String Starting_time;
    ArrayList<String> FineFileNameFromLog;

    public CallRecordReceiver(CallRecord callRecord) {
        this.callRecord = callRecord;
    }

  /*  @Override
    protected void onIncomingCallReceived(Context context, String number, Date start) {

    }

    @Override
    protected void onIncomingCallAnswered(Context context, String number, Date start) {
        startRecord(context, "incoming", number, start);
    }

    @Override
    protected void onIncomingCallEnded(Context context, String number, Date start, Date end) {
        stopRecord(context);
    }

    @Override
    protected void onOutgoingCallStarted(Context context, String number, Date start) {
        startRecord(context, "outgoing", number, start);
    }

    @Override
    protected void onOutgoingCallEnded(Context context, String number, Date start, Date end) {
        stopRecord(context);
    }

    @Override
    protected void onMissedCall(Context context, String number, Date start) {

    }*/

    // Derived classes could override these to respond to specific events of interest
    protected void onRecordingStarted(Context context, CallRecord callRecord, File audioFile) {
    }

    protected void onRecordingFinished(Context context, CallRecord callRecord, File audioFile) {
    }


    private void startRecord(Context context, String seed, String phoneNumber) {
        ConstantUtills.File_Name = phoneNumber;
        try {

            boolean isSaveFile = PrefsHelper.readPrefBool(context, CallRecord.PREF_SAVE_FILE);
            Log.i(TAG, "isSaveFile: " + isSaveFile);

            // dosya kayıt edilsin mi?
            if (!isSaveFile) {
                return;
            }

            String file_name = PrefsHelper.readPrefString(context, CallRecord.PREF_FILE_NAME);
            String dir_path = PrefsHelper.readPrefString(context, CallRecord.PREF_DIR_PATH);
            String dir_name = PrefsHelper.readPrefString(context, CallRecord.PREF_DIR_NAME);
            boolean show_seed = PrefsHelper.readPrefBool(context, CallRecord.PREF_SHOW_SEED);
            boolean show_phone_number = PrefsHelper.readPrefBool(context, CallRecord.PREF_SHOW_PHONE_NUMBER);
            int output_format = PrefsHelper.readPrefInt(context, CallRecord.PREF_OUTPUT_FORMAT);
            int audio_source = PrefsHelper.readPrefInt(context, CallRecord.PREF_AUDIO_SOURCE);
            int audio_encoder = PrefsHelper.readPrefInt(context, CallRecord.PREF_AUDIO_ENCODER);

            File sampleDir = new File(dir_path + "/" + dir_name);

            if (!sampleDir.exists()) {
                sampleDir.mkdirs();
            }


            StringBuilder fileNameBuilder = new StringBuilder();
            fileNameBuilder.append(file_name);
            fileNameBuilder.append("_");

            if (show_seed) {
                fileNameBuilder.append(seed);
                fileNameBuilder.append("_");
            }

            if (show_phone_number) {
                fileNameBuilder.append(phoneNumber);
                fileNameBuilder.append("_");
            }


            //   file_name = fileNameBuilder.toString();

            if (phoneNumber != null) {
                file_name = phoneNumber;
            } else {
                file_name = phoneNumber;
            }


            String suffix = "";
            switch (output_format) {
                case MediaRecorder.OutputFormat.AMR_NB: {
                    suffix = ".amr";
                    break;
                }
                case MediaRecorder.OutputFormat.AMR_WB: {
                    suffix = ".amr";
                    break;
                }
                case MediaRecorder.OutputFormat.MPEG_4: {
                    suffix = ".mp4";
                    break;
                }
                case MediaRecorder.OutputFormat.THREE_GPP: {
                    suffix = ".3gp";
                    break;
                }
                default: {
                    suffix = ".amr";
                    break;
                }
            }

            //  audiofile = File.createTempFile(file_name, suffix, sampleDir);
            audiofile = new File(sampleDir, file_name + ".mp3");
            //   String fileName = sampleDir.getAbsolutePath() + phoneNumber + ".mp3";
            Log.d("File_status", String.valueOf(audiofile));
            recorder = new MediaRecorder();
            recorder.setAudioSource(audio_source);
            recorder.setOutputFormat(output_format);
            recorder.setAudioEncoder(audio_encoder);

            /*recorder.setAudioSamplingRate(44100);
            recorder.setAudioEncodingBitRate(128000);*/
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int i = audioManager.getRouting(1);
            audioManager.setMode(AudioManager.STREAM_VOICE_CALL);
            audioManager.setMicrophoneMute(false);
            audioManager.setSpeakerphoneOn(false);
            //  audioManager.setSpeakerphoneOn(true);
            recorder.setOutputFile(audiofile.getAbsolutePath());
            //audioManager.setMode(AudioManager.MODE_CURRENT);
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0);
            recorder.prepare();
            // Thread.sleep(2000);
            recorder.start();


            isRecordStarted = true;
            onRecordingStarted(context, callRecord, audiofile);

            Log.i(TAG, "record start");

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecord(final Context context) {
        if (recorder != null && isRecordStarted) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
            audioManager.setMode(AudioManager.MODE_NORMAL);
            isRecordStarted = false;
            onRecordingFinished(context, callRecord, audiofile);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        String OldFileName = FILE_SAVE_TIME + ".mp3";
                        String LogData = getFileNameFromLog(context);
                        File dir = new File(Environment.getExternalStorageDirectory() + "/MilesForceMwb");
                        if (dir.exists()) {
                            File from = new File(dir, OldFileName);
                            File to = new File(dir, LogData);
                            Log.d("File_status", String.valueOf(from));
                            Log.d("File_status", String.valueOf(to));
                            if (from.exists())
                                from.renameTo(new File(to + ".mp3"));
                            Log.d("File_status", String.valueOf(new File(to + ".mp3")));
                        }
                    } catch (Exception e) {

                    }

                }
            }, 2000);


            Log.i(TAG, "record stop");
        }
    }

    private String getFileNameFromLog(Context context) {
        FineFileNameFromLog = new ArrayList<>();
        String FileNameFromLog = null;
        StringBuffer sb = new StringBuffer();
        String str = null;
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC limit 10;");
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        try {
            while (managedCursor.moveToNext()) {
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String millisInString = dateFormat.format(callDayTime);
                Date convertedData = dateFormat.parse(millisInString);
                long epoch = convertedData.getTime();
                str = String.valueOf(epoch);
                FineFileNameFromLog.add(str);

            }
        } catch (Exception e) {

        }
        for (int a = 0; a < FineFileNameFromLog.size(); a++) {
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 1000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 2000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 3000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 4000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 5000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 6000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) + 7000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }


            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME)))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 1000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 2000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 3000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 4000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 5000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 6000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }
            if (FineFileNameFromLog.get(a).equals(((String.valueOf(Long.valueOf(FILE_SAVE_TIME) - 7000))))) {
                FileNameFromLog = FineFileNameFromLog.get(a);
            }

        }
        return FileNameFromLog;
    }

//    @Override
//    protected void onCallRecievedCallReceived(Context context, String number, String start) {
//        startRecord(context, number, start);
//    }
//
//    @Override
//    protected void onCallEndded(Context context) {
//        stopRecord(context);
//    }

}
