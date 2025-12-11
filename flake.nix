{
  description = "A Nix flake for an Android development shell environment.";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs =
    {
      self,
      nixpkgs,
      flake-utils,
      ...
    }@inputs:

    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs {
          inherit system;
          config.allowUnfree = true;
        };

        mkScript =
          name: text:
          let
            script = pkgs.writeShellScriptBin name text;
          in
          script;

        scripts = [
          (mkScript "android-start" ''
            nohup android-studio > /dev/null 2>&1 &
          '')
        ];

        # "ANDROID_HOME" = "${android-sdk}/share/android-sdk";
        # "ANDROID_SDK_ROOT" = "${android-sdk}/share/android-sdk";
        # "JAVA_HOME" = jdk.home;

        devPackages = with nixpkgs; [
          pkgs.android-studio
          pkgs.android-tools
          # pkgs.build-tools-34-0-0
          # pkgs.cmdline-tools-latest
          # pkgs.emulator
          # pkgs.platform-tools
          # pkgs.platforms-android-34
          # pkgs.android-sdk
          # pkgs.gradle
          # pkgs.jdk
        ];

      in
      {
        devShells = {
          default = pkgs.mkShell {
            name = "android-shell";
            nativeBuildInputs = scripts;
            packages = devPackages;
          };
        };
      }
    );
}
