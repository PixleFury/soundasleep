package pixlefury.soundasleep

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
	private lateinit var generator: AudioGenerator
	private var playDuration: Long = 2L * 60 * 60 * 1000
	private var firstPlay = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		generator = AudioGenerator(NoiseConfig.BROWN_NOISE.noise)
		generator.volume = NoiseConfig.BROWN_NOISE.volume

		val toggleAudioButton = findViewById<ToggleButton>(R.id.btn_toggle_audio)
		toggleAudioButton.isEnabled = false
		toggleAudioButton.setOnCheckedChangeListener { _, checked ->
			if (checked) {
				generator.play(playDuration)
			} else {
				generator.stop()
			}
		}

		for (config in NoiseConfig.values()) {
			val btn = findViewById<Button>(config.buttonId)
			btn.setOnClickListener {
				generator.noise = config.noise
				generator.volume = config.volume

				if (firstPlay) {
					firstPlay = false
					toggleAudioButton.isEnabled = true
					toggleAudioButton.performClick()
				}
			}
		}

		val headerWave = getDrawable(R.drawable.animated_wave) as AnimatedVectorDrawable
		val footerWave = getDrawable(R.drawable.animated_wave_rev) as AnimatedVectorDrawable
		findViewById<ImageView>(R.id.header_wave).background = headerWave
		findViewById<ImageView>(R.id.footer_wave).background = footerWave
		headerWave.start()
		footerWave.start()

//		findViewById<SeekBar>(R.id.volume_slider).apply {
//			setOnSeekBarChangeListener(this@MainActivity)
//			progress = 50
//		}
	}

	override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
		generator.volume = progress.toFloat() / 100f

//		var volumeIcon = R.drawable.baseline_volume_24
//		if (generator.volume == 0f) {
//			volumeIcon = R.drawable.baseline_volume_mute_24
//		} else if (generator.volume < 0.25f) {
//			volumeIcon = R.drawable.baseline_volume_low_24
//		}
//		findViewById<ImageView>(R.id.volume_icon).setImageResource(volumeIcon)
	}

	override fun onStartTrackingTouch(seekBar: SeekBar?) {}
	override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}