package com.zaclimon.aceiptv.setup;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.media.tv.TvInputInfo;
import android.widget.Toast;

import com.google.android.media.tv.companionlibrary.ChannelSetupFragment;
import com.google.android.media.tv.companionlibrary.EpgSyncJobService;
import com.zaclimon.aceiptv.R;
import com.zaclimon.aceiptv.auth.AuthActivityTv;
import com.zaclimon.aceiptv.service.AceJobService;

/**
 * Created by isaac on 17-06-11.
 */

public class AceTvInputSetupFragment extends ChannelSetupFragment {

    private static final int ASKING_AUTHENTICATION = 0;

    @Override
    public void onStart() {
        super.onStart();

        Intent authIntent = new Intent(getActivity(), AuthActivityTv.class);
        startActivityForResult(authIntent, ASKING_AUTHENTICATION);
    }

    @Override
    public void onScanStarted() {
        String inputId = getActivity().getIntent().getStringExtra(TvInputInfo.EXTRA_INPUT_ID);
        EpgSyncJobService.cancelAllSyncRequests(getActivity());
        EpgSyncJobService.requestImmediateSync(getActivity(), inputId, new ComponentName(getActivity(), AceJobService.class));
    }

    @Override
    public String getInputId() {
        return (null);
    }

    @Override
    public void onScanFinished() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ASKING_AUTHENTICATION) {
            if (resultCode == Activity.RESULT_OK) {
                onScanStarted();
            } else {
                Toast.makeText(getActivity(), getString(R.string.authentication_not_possible), Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        }

    }
}