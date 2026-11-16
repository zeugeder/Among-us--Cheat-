package com.aubase.offline;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public final class MainActivity extends Activity {
    private final State state = new State();
    private Arena arena;

    static final class State {
        boolean speed, fullLight, longKill, noclip, impostor, tracers;
        float multiplier = 2.0f;
        float playerX = 0.5f, playerY = 0.55f;
    }

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(12, 17, 29));
        getWindow().setNavigationBarColor(Color.rgb(12, 17, 29));

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(16), dp(18), dp(16), dp(12));
        page.setBackgroundColor(Color.rgb(12, 17, 29));

        TextView heading = new TextView(this);
        heading.setText("AMONG US BASE · OFFLINE LAB");
        heading.setTextColor(Color.WHITE);
        heading.setTextSize(20);
        heading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        page.addView(heading);

        TextView sub = new TextView(this);
        sub.setText("Lokale Funktions-Demo – keine Verbindung zum Spiel");
        sub.setTextColor(Color.rgb(155, 173, 198));
        sub.setTextSize(12);
        page.addView(sub);

        arena = new Arena();
        LinearLayout.LayoutParams arenaLp = new LinearLayout.LayoutParams(-1, 0, 1.0f);
        arenaLp.topMargin = dp(12);
        arenaLp.bottomMargin = dp(12);
        page.addView(arena, arenaLp);

        ScrollView scroll = new ScrollView(this);
        LinearLayout controls = new LinearLayout(this);
        controls.setOrientation(LinearLayout.VERTICAL);
        controls.setPadding(dp(12), dp(8), dp(12), dp(8));
        controls.setBackgroundColor(Color.rgb(23, 32, 49));

        addToggle(controls, "Speed Hack · Demo", "Bewegt die Spielfigur in der lokalen Demo schneller.", 0);
        SeekBar speed = new SeekBar(this);
        speed.setMax(90);
        speed.setProgress(10);
        TextView speedLabel = label("Geschwindigkeit: 2.0×");
        controls.addView(speedLabel);
        controls.addView(speed);
        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar b, int p, boolean u) {
                state.multiplier = 1.0f + p / 10.0f;
                speedLabel.setText(String.format(Locale.US, "Geschwindigkeit: %.1f×", state.multiplier));
            }
            public void onStartTrackingTouch(SeekBar b) {}
            public void onStopTrackingTouch(SeekBar b) {}
        });
        addToggle(controls, "Full Light", "Zeigt den gesamten Demoraum hell an.", 1);
        addToggle(controls, "Long Kill", "Vergrößert nur den markierten Demo-Aktionsradius.", 2);
        addToggle(controls, "NoClip", "Erlaubt der Demofigur, Demo-Wände zu passieren.", 3);
        addToggle(controls, "Always Impostor", "Setzt die lokale Demo-Rolle auf Impostor.", 4);
        addToggle(controls, "Tracer ESP", "Zeichnet Linien zu den Demo-Bots.", 5);

        Button reset = new Button(this);
        reset.setText("Demo zurücksetzen");
        reset.setOnClickListener(v -> {
            state.speed = state.fullLight = state.longKill = state.noclip = state.impostor = state.tracers = false;
            state.multiplier = 2.0f;
            state.playerX = 0.5f; state.playerY = 0.55f;
            recreate();
        });
        controls.addView(reset);
        scroll.addView(controls);
        page.addView(scroll, new LinearLayout.LayoutParams(-1, dp(290)));
        setContentView(page);
    }

    private void addToggle(LinearLayout parent, String title, String desc, int id) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);
        TextView t = label(title); t.setTextSize(15); t.setTypeface(null, Typeface.BOLD);
        TextView d = label(desc); d.setTextSize(10); d.setTextColor(Color.rgb(155,173,198));
        texts.addView(t); texts.addView(d);
        row.addView(texts, new LinearLayout.LayoutParams(0, -2, 1));
        Switch sw = new Switch(this);
        sw.setOnCheckedChangeListener((button, checked) -> {
            switch (id) {
                case 0: state.speed = checked; break;
                case 1: state.fullLight = checked; break;
                case 2: state.longKill = checked; break;
                case 3: state.noclip = checked; break;
                case 4: state.impostor = checked; break;
                case 5: state.tracers = checked; break;
            }
            arena.invalidate();
        });
        row.addView(sw);
        parent.addView(row);
        View divider = new View(this);
        divider.setBackgroundColor(Color.rgb(48, 62, 83));
        LinearLayout.LayoutParams dp = new LinearLayout.LayoutParams(-1, 1);
        dp.topMargin = dp(5); dp.bottomMargin = dp(5);
        parent.addView(divider, dp);
    }

    private TextView label(String s) {
        TextView t = new TextView(this); t.setText(s); t.setTextColor(Color.WHITE); return t;
    }
    private int dp(int n) { return Math.round(n * getResources().getDisplayMetrics().density); }

    final class Arena extends View {
        final Paint p = new Paint(3);
        final float[][] bots = {{.22f,.25f},{.78f,.28f},{.72f,.75f},{.25f,.78f}};
        Arena() { super(MainActivity.this); setLayerType(View.LAYER_TYPE_SOFTWARE, null); }
        @Override protected void onDraw(Canvas c) {
            super.onDraw(c);
            float w=getWidth(), h=getHeight();
            p.setColor(Color.rgb(17,25,40)); c.drawRoundRect(0,0,w,h,dp(14),dp(14),p);
            p.setColor(state.fullLight ? Color.rgb(65,91,117) : Color.rgb(31,43,62));
            c.drawRoundRect(w*.08f,h*.10f,w*.92f,h*.90f,dp(12),dp(12),p);
            p.setColor(Color.rgb(100,126,151)); p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(dp(5));
            c.drawRect(w*.08f,h*.10f,w*.92f,h*.90f,p); p.setStyle(Paint.Style.FILL);
            // Interior obstacles in the toy arena.
            p.setColor(state.fullLight ? Color.rgb(92,117,141) : Color.rgb(22,31,47));
            c.drawRoundRect(w*.36f,h*.31f,w*.64f,h*.40f,dp(7),dp(7),p);
            c.drawRoundRect(w*.18f,h*.63f,w*.42f,h*.71f,dp(7),dp(7),p);
            if (!state.fullLight) {
                p.setColor(0xB8000000);
                c.drawCircle(w*.5f,h*.55f,Math.min(w,h)*.20f,p);
            }
            float px=state.playerX*w, py=state.playerY*h;
            if (state.tracers) {
                p.setColor(Color.rgb(0,255,190)); p.setStrokeWidth(dp(2));
                for(float[] b:bots) c.drawLine(px,py,b[0]*w,b[1]*h,p);
            }
            p.setColor(Color.rgb(0,210,235));
            for(float[] b:bots) c.drawCircle(b[0]*w,b[1]*h,dp(10),p);
            p.setColor(state.impostor ? Color.rgb(255,65,75) : Color.rgb(70,160,255));
            c.drawCircle(px,py,dp(13),p);
            p.setColor(Color.WHITE); p.setTextSize(dp(11));
            c.drawText(state.impostor ? "DEMO-ROLLE: IMPOSTOR" : "DEMO-ROLLE: CREWMATE",dp(12),dp(24),p);
            if (state.longKill) {
                p.setColor(0x66FF5252); p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(dp(2));
                c.drawCircle(px,py,dp(75),p); p.setStyle(Paint.Style.FILL);
            }
            p.setColor(Color.WHITE); p.setTextSize(dp(10));
            c.drawText("Figur ziehen zum Bewegen",dp(12),h-dp(12),p);
        }
        private boolean insideObstacle(float x, float y) {
            boolean console = x > .36f && x < .64f && y > .31f && y < .40f;
            boolean crates  = x > .18f && x < .42f && y > .63f && y < .71f;
            return console || crates;
        }

        @Override public boolean onTouchEvent(android.view.MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_DOWN || e.getAction() == MotionEvent.ACTION_MOVE) {
                float targetX = Math.max(.10f, Math.min(.90f, e.getX() / Math.max(1, getWidth())));
                float targetY = Math.max(.12f, Math.min(.88f, e.getY() / Math.max(1, getHeight())));
                float dx = targetX - state.playerX;
                float dy = targetY - state.playerY;
                float factor = state.speed ? state.multiplier : 1.0f;
                float nextX = Math.max(.10f, Math.min(.90f, state.playerX + dx * factor));
                float nextY = Math.max(.12f, Math.min(.88f, state.playerY + dy * factor));

                // In normal demo mode walls block movement; NoClip disables this local collision check.
                if (state.noclip || !insideObstacle(nextX, nextY)) {
                    state.playerX = nextX;
                    state.playerY = nextY;
                }
                invalidate();
                return true;
            }
            return true;
        }
    }
}
