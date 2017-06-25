package com.zaclimon.aceiptv.settings.epgforcesync;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.widget.Toast;

import com.google.android.media.tv.companionlibrary.EpgSyncJobService;
import com.zaclimon.aceiptv.R;
import com.zaclimon.aceiptv.service.AceJobService;
import com.zaclimon.aceiptv.util.AceChannelUtil;

import java.util.List;

/**
 * Created by isaac on 17-06-22.
 */

public class EpgForceSyncGuidedFragment extends GuidedStepFragment {

    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = getString(R.string.force_epg_sync_title);
        String description = getString(R.string.force_epg_sync_description);

        return (new GuidanceStylist.Guidance(title, description, null, null));
    }

    @Override
    public void onCreateActions (List<GuidedAction> actions, Bundle savedInstanceState) {
        GuidedAction.Builder yesAction = new GuidedAction.Builder(getActivity());
        GuidedAction.Builder noAction = new GuidedAction.Builder(getActivity());
        yesAction.title(R.string.yes_text);
        noAction.title(R.string.no_text);
        yesAction.id(GuidedAction.ACTION_ID_YES);
        noAction.id(GuidedAction.ACTION_ID_NO);
        actions.add(yesAction.build());
        actions.add(noAction.build());
    }

    @Override
    public void onGuidedActionClicked(GuidedAction guidedAction) {
        if (guidedAction.getId() == GuidedAction.ACTION_ID_YES) {
            String inputId = TvContract.buildInputId(AceChannelUtil.TV_INPUT_SERVICE_COMPONENT);
            EpgSyncJobService.requestImmediateSync(getActivity(), inputId, new ComponentName(getActivity(), AceJobService.class));
            Toast.makeText(getActivity(), R.string.channel_update, Toast.LENGTH_LONG).show();
        }
        getActivity().finish();
    }

}
