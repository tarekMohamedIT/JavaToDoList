package Application.Context;

import Application.Factories.ServicesFactory;

public class ApplicationCore {
    private static ServicesFactory _servicesFactory;

    public static ServicesFactory getServices() {
        return _servicesFactory;
    }

    public static void setServices(ServicesFactory servicesFactory) {
        _servicesFactory = servicesFactory;
    }
}
