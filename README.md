# Running Android UI Tests

### Blog posts 

📗 **Part 1** - https://medium.com/medisafe-tech-blog/running-android-ui-tests-53e85e5c8da8

1. Building Execution Plan.
2. Collect Logs, Record Video, Dump DB, Shared Preferences.
3. Add ‘Clear data’ support.
4. Add ‘Clear notifications’ support.
5. Add parameterized support.
6. Run tests by #tags.
7. Dump network stats, battery, alarms and more.
8. All together.

📗 **Part 2** - https://medium.com/medisafe-tech-blog/running-android-ui-tests-part-2-15ef08056d94

9. Parallel tests execution.
10. Grouping following tests. 

### Test Options
We write UI tests same as before. But now, we can add more annotations that will give us more options. 

- `@ClearData` - Clear data via ADB before test execution.
- `@ClearNotifications` - Clear notification bar via ADB before running the test.
- `@Repeat` - Repeat the same test X number of times, when current iteration is passed to the test.
- `@Tags` - You can tag your tests. Later, you can run tests by selected tags only.
- `@Following` - Will enforce grouping following tests on the same device when running in parallel.

### Test example

```java
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);
        
    private final String[] params = new String[]{
            "a", "A", "Aa"
    };

    @Test
    @ClearData
    @Parameterized.Repeat(count = 3)
    @Tags(tags = {"sanity", "small", "sample"})
    public void someTest() throws Exception {
        String param = params[Parameterized.getIndex()];
        assertEquals("a", param.toLowerCase());
    }

    @Test
    @Following
    public void someTestFollowing() throws Exception {
        assertEquals("a", "A".toLowerCase());
    }
}
```

### Artifacts

In addition to new added options, after each failed test, we fetching and building useful files that will help us investigate failed issues better.

- Recording (mp4)
- Logs
- DB (sqlite)
- Shared preferences
- Dumpsys - Netstats, battery, other.


### Run this sample

**Prepare:** <br>
1. Clone the repo.
2. Connect one or more real devices / emulators. <br>
3. Run on Mac / Ubuntu / Anything that has bash 3.2 (and above)

**Run:**
```bash
# ---- assemble and install the app + test apks ----
# build app APK
./gradlew assembleDebug --stacktrace
# build test APK
./gradlew assembleAndroidTest --stacktrace
# install app APK
adb install -r app/build/outputs/apk/app-debug.apk
# install test APK
adb install -r app/build/outputs/apk/app-debug-androidTest.apk

# ---- prepare and run the tests ----
# create tests raw file
./scripts/raw.sh artifacts/raw-tests.txt com.sromku.sample.runtests
# build execution plan and filter by tags
./scripts/plan.sh artifacts/raw-tests.txt artifacts/execution-plan.txt com.sromku.sample.runtests
# run the tests
./scripts/run.sh artifacts/execution-plan.txt artifacts com.sromku.sample.runtests
```

### Detailed docs

- [The flow and script](docs/Scripts.md)
- [Parallel — The regular approach vs. push approach](docs/Parallel.md)
- [Grouping following tests](docs/Following.md)


### Author

[Roman Kushnarenko - sromku](https://github.com/sromku)

### License
Apache 2.0
