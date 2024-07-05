package mg.emberframework.manager.data;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    String url;
    Map<String, Object> data;

    // Class method
    public void addObject(String attribute, Object object) {
        if (getData() == null) {
            setData(new HashMap<>());
        }
        
        getData().put(attribute, object);
    }

    // Constructor
    public ModelView() {
    }

    public ModelView(String url, Map<String, Object> data) {
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
    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
