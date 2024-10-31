# Archive App

A simple Android application that allows users to open archived versions of web pages using the archive.is service. The app integrates with the Android share menu.

## Requirements

- Android 6.0 (API level 24) or higher
- Kotlin 1.5 or higher
- Android Studio 4.0 or higher

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/jonnywesten/unpaywall-mobile.git
   ```

2. Open the project in Android Studio.

3. Build and run the project on an emulator or physical device.

4. Ensure you have internet access to use the archive service.

## Usage

1. Open any browser or app that supports sharing.
2. Long press on a link to bring up the context menu.
3. Select "Open in Archive" from the share options.
4. The app will open the archived version of the page in your default web browser.

## Development

### Project Structure

- app/src/main/java/de/cs/unpaywall/ArchiveActivity.kt: Main activity handling shared intents and displaying archived pages.
- app/src/main/res/layout/activity_archive.xml: Layout file for the ArchiveActivity.
- app/src/main/AndroidManifest.xml: Manifest file declaring app components and permissions.

### Dependencies

- OkHttp: For making HTTP requests.
- Jsoup: For parsing HTML documents.
- AndroidX AppCompat: For backward-compatible UI components.

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request for any improvements or fixes.

1. Fork the repository.
2. Create a new branch (e.g., feature/YourFeature).
3. Make your changes and commit them (e.g., 'Add some feature').
4. Push to the branch (e.g., 'git push origin feature/YourFeature').
5. Open a pull request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

Thanks to archive.is for providing the archiving service.
