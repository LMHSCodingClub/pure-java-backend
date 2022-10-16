{ pkgs }: {
    deps = [
        pkgs.nodejs-16_x
        pkgs.graalvm17-ce
        pkgs.maven
        pkgs.replitPackages.jdt-language-server
        pkgs.replitPackages.java-debug
    ];
}