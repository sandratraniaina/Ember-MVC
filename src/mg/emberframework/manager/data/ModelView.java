package mg.emberframework.manager.data;

import java.util.HashMap;

public class ModelView {
    String url;
    HashMap<String, Object> data;

    // Class method
    public void addObject(String attribute, Object object) {
        if (getData() == null) {
            setData(new HashMap<String, Object>());
        }
        
        getData().put(attribute, object);
    }

    // Constructor
    public ModelView() {
    }

    public ModelView(String url, HashMap<String, Object> data) {
        setUrl(url);
        setData(data);
    }

    // Getters and setters
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public HashMap<String, Object> getData() {
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
