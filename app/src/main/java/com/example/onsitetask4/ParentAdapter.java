package com.example.onsitetask4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class ParentAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final List<String> header_list;
    private final Map<String, List<String>> child_list;
    private final Map<String,List<String>> childOfChildList;

    public ParentAdapter(Context mContext, List<String> header_list, Map<String, List<String>> child_list, Map<String, List<String>> childOfChildList) {
        this.mContext = mContext;
        this.header_list = header_list;
        this.child_list = child_list;

        this.childOfChildList = childOfChildList;
    }


    @Override
    public int getGroupCount() {
        return header_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_list.get(header_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if(convertView == null ){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_item,null);

        }

        TextView headerName = convertView.findViewById(R.id.header_item);
        headerName.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CustomExpandableListView secondLevel = new CustomExpandableListView(mContext);
        String parentNode = (String)getGroup(groupPosition);
        secondLevel.setAdapter(new childAdapter(mContext,child_list.get(parentNode),childOfChildList));

        return  secondLevel;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
