package src.main.calculations;

import src.main.GUI.Launcher;
import src.main.GUI.panes.PaneDate;
import src.main.GUI.panes.PaneSystem;

/**
 * Class that can keep track of time and date
 */
public class TimeEngine {

    /**
     * Array that contains all dates and times
     * 0. seconds
     * 1. minutes
     * 2. hours
     * 3. day
     * 4. month
     * 5. year
     */
    public final int[] timeDate = {0, 0, 0, 1, 4, 2023};

    //  ---   This 
    public int allTheSeconds = 0;

    public TimeEngine() {

    }

    /**
     * Method that adds 1 second to the timeDate int[] for each iteration and calls
     * minute() if 60 seconds is reached
     *
     * @param x The amount of times the method has to be called, in other words the
     *          amount of seconds to be added
     */
    public void seconds(int x) {

        allTheSeconds = allTheSeconds + x;
        for (int i = 0; i < x; i++) {
            if (timeDate[0] == 59) {
                timeDate[0] = 0;
                minutes(1);
            } else
                timeDate[0] += 1;
        }
    }

    /**
     * Method that adds 1 minute to the timeDate int[] for each iteration and calls
     * hours() if 60 minutes is reached
     *
     * @param x The amount of times the method has to be called, in other words the
     *          amount of minutes to be added
     */
    public void minutes(int x) {

        for (int i = 0; i < x; i++) {
            if (timeDate[1] == 59) {
                timeDate[1] = 0;
                hours(1);
            } else
                timeDate[1] += 1;
        }
    }

    /**
     * Method that adds 1 hour to the timeDate int[] for each iteration and calls
     * day() if 24 hours is reached
     *
     * @param x The amount of times the method has to be called, in other words the
     *          amount of hours to be added
     */
    public void hours(int x) {

        for (int i = 0; i < x; i++) {
            if (timeDate[2] == 23) {
                timeDate[2] = 0;
                day(1);
            } else
                timeDate[2] += 1;
        }
    }

    /**
     * Method that adds 1 day to the timeDate int[] for each iteration and calls
     * month() if the full amount of days in the month has been reached. It takes
     * into account months with 28, 30 and 31 days, and also if the given year is a
     * leap year
     *
     * @param x The amount of times the method has to be called, in other words the
     *          amount of days to be added
     */
    public void day(int x) {

        for (int i = 0; i < x; i++) {

            if (Launcher.isGUILaunched()) {
                PaneSystem.addRoutePoints();
            }

            if (timeDate[4] == 1 || timeDate[4] == 3 || timeDate[4] == 5 || timeDate[4] == 7 ||
                    timeDate[4] == 8 || timeDate[4] == 10 || timeDate[4] == 12) {
                if (timeDate[3] == 31) {
                    timeDate[3] = 1;
                    month(1);
                } else {
                    timeDate[3] += 1;
                }
            } else if (timeDate[4] == 4 || timeDate[4] == 6 || timeDate[4] == 9 || timeDate[4] == 11) {
                if (timeDate[3] == 30) {
                    timeDate[3] = 1;
                    month(1);
                } else {
                    timeDate[3] += 1;
                }
            } else if (timeDate[4] == 2) {
                if (timeDate[5] % 4 != 0) {
                    if (timeDate[3] == 28) {
                        timeDate[3] = 1;
                        month(1);
                    } else {
                        timeDate[3] += 1;
                    }
                } else {
                    if (timeDate[3] == 29) {
                        timeDate[3] = 1;
                        month(1);
                    } else {
                        timeDate[3] += 1;
                    }
                }
            }

            if (Launcher.isGUILaunched()) {
                PaneDate.updateDateString();
            }

        }

    }

    /**
     * Method that adds 1 month to the timeDate int[] for each iteration and calls
     * year() if 12 months is reached
     *
     * @param x The amount of times the method has to be called, in other words the
     *          amount of months to be added
     */
    public void month(int x) {

        for (int i = 0; i < x; i++) {
            if (timeDate[4] == 12) {
                timeDate[4] = 1;
                year();
            } else {
                timeDate[4] += 1;
            }

            if (Launcher.isGUILaunched()) {
                PaneDate.updateDateString();
            }
        }

    }

    /**
     * Method that adds 1 year to the timeDate int[] for each iteration
     */
    public void year() {

        timeDate[5] += 1;
        if (Launcher.isGUILaunched()) {
            PaneDate.updateDateString();
        }

    }

    public int[] getTimeDate() {
        return timeDate;
    }

}