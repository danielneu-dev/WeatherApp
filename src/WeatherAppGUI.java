import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.simple.JSONObject;

public class WeatherAppGUI extends JFrame {
  private JSONObject weatherData;

  public WeatherAppGUI() {
    super("Weather App");

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(450, 650);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    addGuiComponents();
  }

  private void addGuiComponents() {
    // search
    JTextField searchTextField = new JTextField();
    searchTextField.setBounds(15, 15, 351, 45);
    searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
    add(searchTextField);

    // weather
    JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
    weatherConditionImage.setBounds(0, 125, 450, 217);
    add(weatherConditionImage);

    // temperature
    JLabel temperatureText = new JLabel("15°C");
    temperatureText.setBounds(0, 350, 450, 54);
    temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
    temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
    add(temperatureText);

    // description
    JLabel weatherConditionDesc = new JLabel("Cloudy");
    weatherConditionDesc.setBounds(0, 405, 450, 36);
    weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
    weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
    add(weatherConditionDesc);

    // humidity
    JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
    humidityImage.setBounds(15, 500, 74, 66);
    add(humidityImage);
    JLabel humidityText = new JLabel("<html><b>Humidity</b><br> 100%</html>");
    humidityText.setBounds(90, 500, 90, 55);
    humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
    add(humidityText);

    // wind speed
    JLabel windImage = new JLabel(loadImage("src/assets/windspeed.png"));
    windImage.setBounds(220, 500, 74, 66);
    add(windImage);
    JLabel windText = new JLabel("<html><b>Windspeed</b><br> 15km/h</html>");
    windText.setBounds(310, 500, 90, 55);
    windText.setFont(new Font("Dialog", Font.PLAIN, 16));
    add(windText);

    JButton searchButton = new JButton(loadImage("src/assets/search.png"));
    searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    searchButton.setBounds(375, 15, 47, 45);
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String userInput = searchTextField.getText();
        if (userInput.replaceAll("\\s", "").length() <= 0) {
          return;
        }
        weatherData = WeatherApp.getWeatherData(userInput);

        // update GUI
        String weatherCondition = (String) weatherData.get("weather_condition");
        switch (weatherCondition) {
          case "Clear":
            weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
            break;
          case "Cloudy":
            weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
            break;
          case "Rain":
            weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
            break;
          case "Snow":
            weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
            break;
        }

        weatherConditionDesc.setText(weatherCondition);

        double temperature = (double) weatherData.get("temperature");
        temperatureText.setText(temperature + "°C");

        long humidity = (long) weatherData.get("humidity");
        humidityText.setText("<html><b>Humidity</b><br> " + humidity + "%</html>");

        double windspeed = (double) weatherData.get("windspeed");
        windText.setText("<html><b>Windspeed</b><br> " + windspeed + "km/h</html>");
      }
    });
    add(searchButton);
  }

  private ImageIcon loadImage(String resourcePath) {
    try {
      BufferedImage image = ImageIO.read(new File(resourcePath));
      return new ImageIcon(image);
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Failed to load image: " + resourcePath);
    return null;
  }
}
