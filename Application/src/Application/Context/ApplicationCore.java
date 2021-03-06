package Application.Context;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationCore {
    private static ConcurrentHashMap<String, Object> _objectsMap;
    private static ConcurrentHashMap<String, Callable<Object>> _objectsFactoriesMap;

    public static <TInterface , TImpl> void registerTypeAs(Class<TInterface> interfaceCLass, TImpl item){
        if (_objectsMap == null)
            _objectsMap = new ConcurrentHashMap<>();

        String name = interfaceCLass.getName();
        if (!_objectsMap.containsKey(name))
            _objectsMap.put(name, item);
    }

    public static <TInterface> void registerFactoryAs(Class<TInterface> interfaceCLass, Callable<Object> factory){
        if (_objectsFactoriesMap == null)
            _objectsFactoriesMap = new ConcurrentHashMap<>();

        String name = interfaceCLass.getName();
        if (!_objectsFactoriesMap.containsKey(name))
            _objectsFactoriesMap.put(name, factory);
    }

    public static <TInterface> TInterface resolve(Class<TInterface> interfaceCLass){
        var resolved = resolveType(interfaceCLass);
        if (resolved != null) return resolved;

        resolved = resolveFactory(interfaceCLass);
        if (resolved != null) return resolved;

        return null;
    }

    public static <TInterface> TInterface resolveType(Class<TInterface> interfaceCLass){
        String name = interfaceCLass.getName();
        try {
            if (_objectsMap != null && _objectsMap.containsKey(name))
                return (TInterface) _objectsMap.get(name);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public static <TInterface> TInterface resolveFactory(Class<TInterface> interfaceCLass){
        String name = interfaceCLass.getName();
        try {
            if (_objectsFactoriesMap != null && _objectsFactoriesMap.containsKey(name))
                return (TInterface) _objectsFactoriesMap.get(name).call();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
